using AutoMapper;
using subject_service.Models.Dtos;

namespace subject_service.Models
{
    public class ApplicationAutoMapperProfile: Profile
    {
        public ApplicationAutoMapperProfile() {
            CreateMap<SubjectCreateUpdateDto, Subject>();
            CreateMap<Subject, SubjectDto>();
            CreateMap<MarkCreateUpdateDto, Mark>();
            CreateMap<Mark, MarkDto>();
            CreateMap<ComponentPointCreateUpdateDto, ComponentPoint>();
            CreateMap<ComponentPoint, ComponentPointDto>();
            CreateMap<SubjectTeacherDto, Subjects_teachers>();
            CreateMap<Subjects_teachers, SubjectTeacherDto>();
        }
    }
}
