using Microsoft.EntityFrameworkCore;
using subject_service.DbMigrator;
using subject_service.Models;
using subject_service.Repository.Interfaces;

namespace subject_service.Repository.Implementations
{
    public class SubjectTeacherRepository : ISubjectTeacherRepository
    {
        private readonly SubjectServiceDbContext _dbContext;

        public SubjectTeacherRepository(SubjectServiceDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<int> AddAsync(Subjects_teachers subjectTeacher)
        {
            try
            {
                (Subjects_teachers, int) item = await GetByIdAsync(subjectTeacher.subject_id, subjectTeacher.teacher_id);
                if (item.Item2 == 1)
                {
                    return 1;
                }
                if (item.Item2 == 0)
                {
                    return 2;
                }
                _dbContext.subjects_teachers.Add(subjectTeacher);
                await _dbContext.SaveChangesAsync();
            }
            catch (Exception ex)
            {
                return 1;
            }
            return 0;
        }

        public async Task<int> DeleteAsync(long subjectId, string teacherId)
        {
            try
            {
                (Subjects_teachers, int) item = await GetByIdAsync(subjectId, teacherId);
                if (item.Item2 == 1)
                {
                    return 1;
                }
                if (item.Item2 == 2)
                {
                    return 2;
                }
                _dbContext.subjects_teachers.Remove(item.Item1);
                await _dbContext.SaveChangesAsync();
            }
            catch (Exception ex)
            {
                return 1;
            }
            return 0;
        }

        public async Task<(IEnumerable<Subjects_teachers>, int)> GetAllAsync()
        {
            try
            {
                var subjectTeacher = await _dbContext.subjects_teachers.ToListAsync();
                return (subjectTeacher, 0);
            }
            catch (Exception ex)
            {
                return (new List<Subjects_teachers>(), 1);
            }
        }

        public async Task<(Subjects_teachers, int)> GetByIdAsync(long subjectId, string teacherId)
        {
            try
            {
                var subjectTeacher = await _dbContext.subjects_teachers.AsNoTracking().FirstOrDefaultAsync(s => s.subject_id == subjectId && s.teacher_id == teacherId);
                if (subjectTeacher == null)
                {
                    return (new(), 2);
                }
                return (subjectTeacher, 0);
            }
            catch (Exception ex)
            {
                return (new(), 1);
            }
        }

        public async Task<int> UpdateAsync(Subjects_teachers subjectTeacher)
        {
            try
            {
                (Subjects_teachers, int) item = await GetByIdAsync(subjectTeacher.subject_id, subjectTeacher.teacher_id);
                if (item.Item2 == 1)
                {
                    return 1;
                }
                if (item.Item2 == 2)
                {
                    return 2;
                }
                _dbContext.subjects_teachers.Update(subjectTeacher);
                await _dbContext.SaveChangesAsync();
            }
            catch (Exception ex)
            {
                return 1;
            }
            return 0;
        }
    }
}
