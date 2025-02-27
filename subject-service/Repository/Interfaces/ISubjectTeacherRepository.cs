using subject_service.Models;

namespace subject_service.Repository.Interfaces
{
    public interface ISubjectTeacherRepository
    {
        Task<(IEnumerable<Subjects_teachers>, int)> GetAllAsync();
        Task<(Subjects_teachers, int)> GetByIdAsync(long subjectId, string teacherId);
        Task<int> AddAsync(Subjects_teachers subjectTeacher);
        Task<int> UpdateAsync(Subjects_teachers subjectTeacher);
        Task<int> DeleteAsync(long subjectId, string teacherId);
    }
}
