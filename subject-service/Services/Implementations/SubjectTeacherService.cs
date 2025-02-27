using subject_service.Models;
using subject_service.Repository.Interfaces;
using subject_service.Services.Interfaces;

namespace subject_service.Services.Implementations
{
    public class SubjectTeacherService : ISubjectTeacherService
    {
        private readonly ISubjectTeacherRepository subjectTeacherRepository;

        public SubjectTeacherService(ISubjectTeacherRepository repository)
        {
            subjectTeacherRepository = repository;
        }

        public Task<int> AddSubjectTeacher(Subjects_teachers subjectTeacher)
        {
            return subjectTeacherRepository.AddAsync(subjectTeacher);
        }

        public Task<int> DeleteSubjectTeacher(long subjectId, string teacherId)
        {
            return subjectTeacherRepository.DeleteAsync(subjectId, teacherId);
        }

        public Task<(IEnumerable<Subjects_teachers>, int)> GetAllSubjectsTeachers()
        {
            return subjectTeacherRepository.GetAllAsync();
        }

        public Task<(Subjects_teachers, int)> GetSubjectTeacherById(long subjectId, string teacherId)
        {
            return subjectTeacherRepository.GetByIdAsync(subjectId, teacherId);
        }

        public Task<int> UpdateSubjectTeacher(Subjects_teachers subjectTeacher)
        {
            return subjectTeacherRepository.UpdateAsync(subjectTeacher);
        }
    }
}
