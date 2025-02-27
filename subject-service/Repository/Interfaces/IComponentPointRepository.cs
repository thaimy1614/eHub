using subject_service.Models;

namespace subject_service.Repository.Interfaces
{
    public interface IComponentPointRepository
    {
        Task<(IEnumerable<ComponentPoint>, int)> GetAllAsync();
        Task<(ComponentPoint, int)> GetByIdAsync(long id);
        Task<int> AddAsync(ComponentPoint componentPoint);
        Task<int> UpdateAsync(ComponentPoint componentPoint);
        Task<int> DeleteAsync(long id);
    }
}
