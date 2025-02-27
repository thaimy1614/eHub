using Microsoft.EntityFrameworkCore;
using subject_service.DbMigrator;
using subject_service.Models;
using subject_service.Repository.Interfaces;

namespace subject_service.Repository.Implementations
{
    public class MarkRepository : IMarkRepository
    {
        private readonly SubjectServiceDbContext _dbContext;

        public MarkRepository(SubjectServiceDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<int> AddAsync(Mark mark)
        {
            try
            {
                _dbContext.marks.Add(mark);
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
                (Mark, int) item = await GetByIdAsync(id);
                if (item.Item2 == 1)
                {
                    return 1;
                }
                if (item.Item2 == 2)
                {
                    return 2;
                }
                _dbContext.marks.Remove(item.Item1);
                await _dbContext.SaveChangesAsync();
            }
            catch (Exception ex)
            {
                return 1;
            }
            return 0;
        }

        public async Task<(IEnumerable<Mark>, int)> GetAllAsync()
        {
            try
            {
                var mark = await _dbContext.marks.Include(s => s.ComponentPoints).ToListAsync();
                return (mark, 0);
            }
            catch (Exception ex)
            {
                return (new List<Mark>(), 1);
            }
        }

        public async Task<(Mark, int)> GetByIdAsync(long id)
        {
            try
            {
                var mark = await _dbContext.marks.AsNoTracking().FirstOrDefaultAsync(s => s.id == id);
                if (mark == null)
                {
                    return (new(), 2);
                }
                return (mark, 0);
            }
            catch (Exception ex)
            {
                return (new(), 1);
            }
        }

        public async Task<(Mark, int)> GetBySubjectIdStudentIdAsync(long subjectId,string studentId)
        {
            try
            {
                var mark = await _dbContext.marks.AsNoTracking().FirstOrDefaultAsync(s => s.subject_id == subjectId && s.student_id == studentId);
                if (mark == null)
                {
                    return (new(), 2);
                }
                return (mark, 0);
            }
            catch (Exception ex)
            {
                return (new(), 1);
            }
        }

        public async Task<int> UpdateAsync(Mark mark)
        {
            try
            {
                (Mark, int) item = await GetByIdAsync(mark.id);
                if (item.Item2 == 1)
                {
                    return 1;
                }
                if (item.Item2 == 2)
                {
                    return 2;
                }
                _dbContext.marks.Update(mark);
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
