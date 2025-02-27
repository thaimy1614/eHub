using subject_service.Models;

namespace subject_service.Services.Interfaces
{
    public interface ISubjectTeacherService
    {
        Task<(IEnumerable<Subjects_teachers>, int)> GetAllSubjectsTeachers();
        Task<(Subjects_teachers, int)> GetSubjectTeacherById(long subjectId, string teacherId);
        Task<int> AddSubjectTeacher(Subjects_teachers subjectTeacher);
        Task<int> UpdateSubjectTeacher(Subjects_teachers subjectTeacher);
        Task<int> DeleteSubjectTeacher(long subjectId, string teacherId);
    }
}
