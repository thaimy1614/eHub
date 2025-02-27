using subject_service.Models;
using subject_service.Repository.Interfaces;
using subject_service.Services.Interfaces;

namespace subject_service.Services.Implementations
{
    public class ComponentPointService : IComponentPointService
    {
        private readonly IComponentPointRepository componentPointRepository;

        public ComponentPointService(IComponentPointRepository repository)
        {
            componentPointRepository = repository;
        }

        public async Task<int> AddComponentPoint(ComponentPoint componentPoint)
        {
            return await componentPointRepository.AddAsync(componentPoint);
        }

        public async Task<int> DeleteComponentPoint(long id)
        {
            return await componentPointRepository.DeleteAsync(id);
        }

        public async Task<(IEnumerable<ComponentPoint>, int)> GetAllComponents()
        {
            return await componentPointRepository.GetAllAsync();
        }

        public Task<(ComponentPoint, int)> GetComponentPointById(long id)
        {
            return componentPointRepository.GetByIdAsync(id);
        }

        public Task<int> UpdateComponentPoint(ComponentPoint componentPoint)
        {
            return componentPointRepository.UpdateAsync(componentPoint);
        }
    }
}
