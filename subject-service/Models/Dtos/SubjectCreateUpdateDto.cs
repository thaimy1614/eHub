using subject_service.Models;
using System.Text.Json.Serialization;

namespace subject_service.Models.Dtos
{
    public partial class SubjectCreateUpdateDto
    {
        public long id { get; set; } 

        public string name { get; set; }

        public long grade_id { get; set; }

        public int total_sessions { get; set; }

        public long school_year_id { get; set; }
    }
}
