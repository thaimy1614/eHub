using subject_service.Models;

namespace subject_service.Repository.Interfaces
{
    public interface IMarkRepository
    {
        Task<(IEnumerable<Mark>, int)> GetAllAsync();
        Task<(Mark, int)> GetByIdAsync(long id);
        Task<(Mark, int)> GetBySubjectIdStudentIdAsync(long subjectId, string studentId);
        Task<int> AddAsync(Mark mark);
        Task<int> UpdateAsync(Mark mark);
        Task<int> DeleteAsync(long id);
    }
}
