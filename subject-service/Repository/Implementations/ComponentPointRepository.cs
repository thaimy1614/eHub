using Microsoft.EntityFrameworkCore;
using subject_service.DbMigrator;
using subject_service.Models;
using subject_service.Repository.Interfaces;

namespace subject_service.Repository.Implementations
{
    public class ComponentPointRepository : IComponentPointRepository
    {
        private readonly SubjectServiceDbContext _dbContext;

        public ComponentPointRepository(SubjectServiceDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<int> AddAsync(ComponentPoint componentPoint)
        {
            try
            {
                _dbContext.component_points.Add(componentPoint);
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
                (ComponentPoint, int) item = await GetByIdAsync(id);
                if (item.Item2 == 1)
                {
                    return 1;
                }
                if (item.Item2 == 2)
                {
                    return 2;
                }

                _dbContext.component_points.Remove(item.Item1);
                await _dbContext.SaveChangesAsync();
            }
            catch (Exception ex)
            {
                return 1;
            }
            return 0;
        }

        public async Task<(IEnumerable<ComponentPoint>, int)> GetAllAsync()
        {
            try
            {
                var componentPoint = await _dbContext.component_points.ToListAsync();
                return (componentPoint, 0);
            }
            catch (Exception ex)
            {
                return (new List<ComponentPoint>(), 1);
            }
        }

        public async Task<(ComponentPoint, int)> GetByIdAsync(long id)
        {
            try
            {
                var componentPoint = await _dbContext.component_points.AsNoTracking().FirstOrDefaultAsync(s => s.id == id);
                if (componentPoint == null)
                {
                    return (new(), 2);
                }
                return (componentPoint, 0);
            }
            catch (Exception ex)
            {
                return (new(), 1);
            }
        }

        public async Task<int> UpdateAsync(ComponentPoint componentPoint)
        {
            try
            {
                (ComponentPoint, int) item = await GetByIdAsync(componentPoint.id);
                if (item.Item2 == 1)
                {
                    return 1;
                }
                if (item.Item2 == 2)
                {
                    return 2;
                }
                componentPoint.is_update = true;
                _dbContext.component_points.Update(componentPoint);
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
