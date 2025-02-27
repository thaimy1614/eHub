using subject_service.Models;

namespace subject_service.Repository.Interfaces
{
    public interface ISubjectRepository
    {
        Task<(IEnumerable<Subject>, int)> GetAllAsync();
        Task<(Subject, int)> GetByIdAsync(long id);
        Task<(Subject, int)> GetByNameAndGradeIdAsync(Subject subject);
        Task<int> AddAsync(Subject subject);
        Task<int> UpdateAsync(Subject subject);
        Task<int> DeleteAsync(long id);
    }
}
