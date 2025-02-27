using subject_service.Models;
using subject_service.Repository.Interfaces;
using subject_service.Services.Interfaces;

namespace subject_service.Services.Implementations
{
    public class MarkService : IMarkService
    {
        private readonly IMarkRepository markRepository;

        public MarkService(IMarkRepository repository)
        {
            markRepository = repository;
        }

        public Task<int> AddMark(Mark mark)
        {
            return markRepository.AddAsync(mark);
        }

        public Task<int> DeleteMark(long id)
        {
            return markRepository.DeleteAsync(id);
        }

        public Task<(IEnumerable<Mark>, int)> GetAllMarks()
        {
            return markRepository.GetAllAsync();
        }

        public Task<(Mark, int)> GetMarkById(long id)
        {
            return markRepository.GetByIdAsync(id);
        }

        public Task<(Mark, int)> GetMarkBySubjectIdStudentIdAsync(long subjectId, string studentId)
        {
            return markRepository.GetBySubjectIdStudentIdAsync(subjectId, studentId);
        }

        public Task<int> UpdateMark(Mark mark)
        {
            return markRepository.UpdateAsync(mark);
        }

    }
}
