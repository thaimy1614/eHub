using subject_service.Models;

namespace subject_service.Services.Interfaces
{
    public interface IComponentPointService
    {
        Task<(IEnumerable<ComponentPoint>, int)> GetAllComponents();
        Task<(ComponentPoint, int)> GetComponentPointById(long id);
        Task<int> AddComponentPoint(ComponentPoint componentPoint);
        Task<int> UpdateComponentPoint(ComponentPoint componentPoint);
        Task<int> DeleteComponentPoint(long id);
    }
}
