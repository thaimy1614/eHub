using subject_service.Models;

namespace subject_service.Services.Interfaces
{
    public interface IMarkService
    {
        Task<(IEnumerable<Mark>, int)> GetAllMarks();
        Task<(Mark, int)> GetMarkById(long id);
        Task<(Mark, int)> GetMarkBySubjectIdStudentIdAsync(long subjectId, string studentId);
        Task<int> AddMark(Mark mark);
        Task<int> UpdateMark(Mark mark);
        Task<int> DeleteMark(long id);
    }
}
