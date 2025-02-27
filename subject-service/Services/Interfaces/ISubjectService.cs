using subject_service.Models;

namespace subject_service.Services.Interfaces
{
    public interface ISubjectService
    {
        Task<(IEnumerable<Subject>, int)> GetAllSubjects();
        Task<(Subject, int)> GetSubjectById(long id);
        Task<int> AddSubject (Subject subject);
        Task<int> UpdateSubject(Subject subject);
        Task<int> DeleteSubject (long id);
    }
}
