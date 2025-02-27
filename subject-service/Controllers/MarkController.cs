using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using subject_service.HttpApi;
using subject_service.Models.Dtos;
using subject_service.Models;
using subject_service.Services.Interfaces;

namespace subject_service.Controllers
{
    [Route("subject/mark")]
    [ApiController]
    public class MarkController : ControllerBase
    {
        private readonly IMarkService _markService;
        private readonly IMapper _mapper;

        public MarkController(IMarkService markService, IMapper mapper)
        {
            _markService = markService;
            _mapper = mapper;
        }

        [HttpGet("get-all")]
        public async Task<IActionResult> GetAll()
        {
            var item = await _markService.GetAllMarks();
            var markDto = _mapper.Map<List<MarkDto>>(item.Item1);

            string code = $"100{item.Item2}";

            var response = new ApiResponse<IEnumerable<MarkDto>>
            {
                Code = code,
                Result = (IEnumerable<MarkDto>)markDto
            };
            return Ok(response);
        }

        [HttpGet("get-mark-by-id/subjectId/{subjectId}/studentId/{studentId}")]
        public async Task<IActionResult> GetMarkBySubjectIdStudentId(long subjectId, string studentId)
        {
            var item = await _markService.GetMarkBySubjectIdStudentIdAsync(subjectId, studentId);
            var markDto = _mapper.Map<MarkDto>(item.Item1);

            string code = $"100{item.Item2}";

            var response = new ApiResponse<MarkDto>
            {
                Code = code,
                Result = markDto
            };
            return Ok(response);
        }

        [HttpPost("add")]
        public async Task<IActionResult> Add(MarkCreateUpdateDto markCreateDto)
        {
            if (markCreateDto == null)
            {
                var errorResponse = new ApiResponse<object>
                {
                    Code = "1002",
                    Result = null
                };
                return Ok(errorResponse);
            }

            var mark = _mapper.Map<Mark>(markCreateDto);

            var codeResult = await _markService.AddMark(mark);
            string code = $"100{codeResult}";

            var response = new ApiResponse<Mark>
            {
                Code = code,
                Result = mark
            };
            return Ok(response);
        }

        [HttpPut("edit/{id}")]
        public async Task<IActionResult> Edit(MarkCreateUpdateDto markUpdateDto)
        {
            if (markUpdateDto == null)
            {
                var errorResponse = new ApiResponse<object>
                {
                    Code = "1003",
                    Result = null
                };
                return Ok(errorResponse);
            }

            var mark = _mapper.Map<Mark>(markUpdateDto);

            var codeResult = await _markService.UpdateMark(mark);
            string code = $"100{codeResult}";

            var response = new ApiResponse<Mark>
            {
                Code = code,
                Result = mark
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

            var codeResult = await _markService.DeleteMark(id);
            string code = $"100{codeResult}";

            var response = new ApiResponse<Mark>
            {
                Code = code,
                Result = null
            };
            return Ok(response);
        }
    }
}
