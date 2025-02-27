using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using subject_service.HttpApi;
using subject_service.Models.Dtos;
using subject_service.Models;
using subject_service.Services.Interfaces;

namespace subject_service.Controllers
{
    [Route("subject/componentpoint")]
    [ApiController]
    public class ComponentPointController : ControllerBase
    {
        private readonly IComponentPointService _componentPointService;
        private readonly IMapper _mapper;

        public ComponentPointController(IComponentPointService componentPointService, IMapper mapper)
        {
            _componentPointService = componentPointService;
            _mapper = mapper;
        }

        [HttpGet("get-all")]
        public async Task<IActionResult> GetAll()
        {
            var item = await _componentPointService.GetAllComponents();
            var componentPointDto = _mapper.Map<List<ComponentPointDto>>(item.Item1);

            string code = $"100{item.Item2}";

            var response = new ApiResponse<IEnumerable<ComponentPointDto>>
            {
                Code = code,
                Result = (IEnumerable<ComponentPointDto>)componentPointDto
            };
            return Ok(response);
        }

        [HttpPost("add")]
        public async Task<IActionResult> Add(ComponentPointCreateUpdateDto componentPointCreateDto)
        {
            if (componentPointCreateDto == null)
            {
                var errorResponse = new ApiResponse<object>
                {
                    Code = "1002",
                    Result = null
                };
                return Ok(errorResponse);
            }

            var componentPoint = _mapper.Map<ComponentPoint>(componentPointCreateDto);

            var codeResult = await _componentPointService.AddComponentPoint(componentPoint);
            string code = $"100{codeResult}";

            var response = new ApiResponse<ComponentPoint>
            {
                Code = code,
                Result = componentPoint
            };
            return Ok(response);
        }

        [HttpPut("edit/{id}")]
        public async Task<IActionResult> Edit(ComponentPointCreateUpdateDto componentPointUpdateDto)
        {
            if (componentPointUpdateDto == null)
            {
                var errorResponse = new ApiResponse<object>
                {
                    Code = "1003",
                    Result = null
                };
                return Ok(errorResponse);
            }

            var componentPoint = _mapper.Map<ComponentPoint>(componentPointUpdateDto);

            var codeResult = await _componentPointService.UpdateComponentPoint(componentPoint);
            string code = $"100{codeResult}";

            var response = new ApiResponse<ComponentPoint>
            {
                Code = code,
                Result = componentPoint
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

            var codeResult = await _componentPointService.DeleteComponentPoint(id);
            string code = $"100{codeResult}";

            var response = new ApiResponse<object>
            {
                Code = code,
                Result = null
            };
            return Ok(response);
        }
    }
}
