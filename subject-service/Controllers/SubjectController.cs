using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using subject_service.HttpApi;
using subject_service.Models;
using subject_service.Models.Dtos;
using subject_service.Services.Interfaces;

namespace subject_service.Controllers
{
    [Route("subject")]
    [ApiController]
    public class SubjectController : ControllerBase
    {
        private readonly ISubjectService _subjectService;
        private readonly ISubjectTeacherService _subjectTeacherService;
        private readonly IMapper _mapper;

        public SubjectController(ISubjectService subjectService, ISubjectTeacherService subjectTeacherService, IMapper mapper)
        {
            _subjectService = subjectService;
            _subjectTeacherService = subjectTeacherService;
            _mapper = mapper;
        }

        [HttpGet("get-all")]
        public async Task<IActionResult> GetAll()
        {
            var item = await _subjectService.GetAllSubjects();
            var subjectDto = _mapper.Map<List<SubjectDto>>(item.Item1);

            string code = $"100{item.Item2}";
            
            var response = new ApiResponse<IEnumerable<SubjectDto>>
            {
                Code = code,
                Result = (IEnumerable<SubjectDto>)subjectDto
            };
            return Ok(response);
        }

        [HttpPost("add")]
        public async Task<IActionResult> Add(SubjectCreateUpdateDto subjectCreateDto)
        {
            if (subjectCreateDto == null)
            {
                var errorResponse = new ApiResponse<object>
                {
                    Code = "1002",
                    Result = null
                };
                return Ok(errorResponse);
            }

            var subject = _mapper.Map<Subject>(subjectCreateDto);

            var codeResult = await _subjectService.AddSubject(subject);
            string code = $"100{codeResult}";

            var response = new ApiResponse<Subject>
            {
                Code = code,
                Result = subject
            };
            return Ok(response);
        }
        
        [HttpPost("assign-teacher-to-subject")]
        public async Task<IActionResult> AssignTeacherToSubject(SubjectTeacherDto subjectTeacherDto)
        {
            if (subjectTeacherDto == null)
            {
                var errorResponse = new ApiResponse<object>
                {
                    Code = "1003",
                    Result = null
                };
                return Ok(errorResponse);
            }

            var subjectTeacher = _mapper.Map<Subjects_teachers>(subjectTeacherDto);

            var codeResult = await _subjectTeacherService.AddSubjectTeacher(subjectTeacher);
            string code = $"100{codeResult}";

            var response = new ApiResponse<Subjects_teachers>
            {
                Code = code,
                Result = subjectTeacher
            };
            return Ok(response);
        }

        [HttpPut("edit/{id}")]
        public async Task<IActionResult> Edit(SubjectCreateUpdateDto subjectUpdateDto)
        {
            if (subjectUpdateDto == null)
            {
                var errorResponse = new ApiResponse<object>
                {
                    Code = "1003",
                    Result = null
                };
                return Ok(errorResponse);
            }

            var subject = _mapper.Map<Subject>(subjectUpdateDto);

            var codeResult = await _subjectService.UpdateSubject(subject);
            string code = $"100{codeResult}";

            var response = new ApiResponse<Subject>
            {
                Code = code,
                Result = subject
            };
            return Ok(response);
        }

        [HttpDelete("delete/{id}")]
        public async Task<IActionResult> Delete(long id)
        {
            if (id == null)
            {
                var errorResponse = new ApiResponse<object>
                {
                    Code = "1003",
                    
                    Result = null
                };
                return Ok(errorResponse);
            }

            var codeResult = await _subjectService.DeleteSubject(id);
            string code = $"100{codeResult}";

            var response = new ApiResponse<Subject>
            {
                Code = code,
                Result = null
            };
            return Ok(response);
        }
    }
}
