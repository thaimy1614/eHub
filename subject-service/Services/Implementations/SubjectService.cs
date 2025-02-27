using subject_service.Models;
using subject_service.Repository.Interfaces;
using subject_service.Services.Interfaces;

namespace subject_service.Services.Implementations
{
    public class SubjectService : ISubjectService
    {
        private readonly ISubjectRepository subjectRepository;

        public SubjectService(ISubjectRepository repository) { 
            subjectRepository = repository;
        }

        public async Task<int> AddSubject(Subject subject)
        {
            var item = await subjectRepository.GetByNameAndGradeIdAsync(subject);
            if(item.Item2 == 0)
            {
                subject.id = item.Item1.id;
                return await subjectRepository.UpdateAsync(subject);
            }
            else if (item.Item2 == 1) {
                return 1;
            }
            else
            {
                return await subjectRepository.AddAsync(subject);
            }
        }

        public async Task<int> DeleteSubject(long id)
        {
            return await subjectRepository.DeleteAsync(id);
        }

        public async Task<(IEnumerable<Subject>, int)> GetAllSubjects()
        {
            return await subjectRepository.GetAllAsync();
        }

        public Task<(Subject, int)> GetSubjectById(long id)
        {
            return subjectRepository.GetByIdAsync(id);  
        }

        public Task<int> UpdateSubject(Subject subject)
        {
            return subjectRepository.UpdateAsync(subject);
        }
    }
}
