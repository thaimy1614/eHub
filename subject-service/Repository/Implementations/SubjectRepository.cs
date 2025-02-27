using Microsoft.EntityFrameworkCore;
using subject_service.DbMigrator;
using subject_service.Models;
using subject_service.Repository.Interfaces;
using System;

namespace subject_service.Repository.Implementations
{
    public class SubjectRepository : ISubjectRepository
    {
        private readonly SubjectServiceDbContext _dbContext;

        public SubjectRepository(SubjectServiceDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<int> AddAsync(Subject subject)
        {
            try
            {
                _dbContext.subjects.Add(subject);
                await _dbContext.SaveChangesAsync();
            }
            catch (Exception ex)
            {
                return 1;
            }
            return 0;
        }

        public async Task<int> DeleteAsync(long id)
        {
            try
            {
                (Subject, int) item = await GetByIdAsync(id);
                if (item.Item2 == 1) {
                    return 1;
                }
                if (item.Item2 == 2) { 
                    return 2;
                }
                item.Item1.is_deleted = true;
                _dbContext.subjects.Update(item.Item1);
                await _dbContext.SaveChangesAsync();
            }
            catch (Exception ex)
            {
                return 1;
            }
            return 0;
        }

        public async Task<(IEnumerable<Subject>, int)> GetAllAsync()
        {
            try
            {
                var subject = await _dbContext.subjects.Where(s => ! s.is_deleted).Include(g => g.Subjects_teachers).ToListAsync();
                return (subject, 0);
            }
            catch (Exception ex)
            {
                return (new List<Subject>(), 1);
            }
        }

        public async Task<(Subject, int)> GetByIdAsync(long id)
        {
            try
            {
                var subject = await _dbContext.subjects.AsNoTracking().FirstOrDefaultAsync(s => s.id == id);
                if (subject == null) {
                    return (new(), 2);
                }
                return (subject, 0);
            }
            catch (Exception ex)
            {
                return (new(), 1);
            }
        }
        
        public async Task<(Subject, int)> GetByNameAndGradeIdAsync(Subject item)
        {
            try
            {
                var subject = await _dbContext.subjects.AsNoTracking().FirstOrDefaultAsync(s => s.name == item.name && s.grade_id == item.grade_id);
                if (subject == null) {
                    return (new(), 2);
                }
                return (subject, 0);
            }
            catch (Exception ex)
            {
                return (new(), 1);
            }
        }

        public async Task<int> UpdateAsync(Subject subject)
        {
            try
            {
                (Subject, int) item = await GetByIdAsync(subject.id);
                if (item.Item2 == 1)
                {
                    return 1;
                }
                if (item.Item2 == 2)
                {
                    return 2;
                }
                _dbContext.subjects.Update(subject);
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
