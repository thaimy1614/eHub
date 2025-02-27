using subject_service.Models;
using System.Text.Json.Serialization;

namespace subject_service.Models.Dtos
{
    public partial class SubjectDto
    {
        public long id { get; set; }

        public string name { get; set; }

        public long grade_id { get; set; }

        public int? total_sessions { get; set; }

        public int total_teacher;

        public long school_year_id { get; set; }

        public virtual ICollection<Mark> Marks { get; set; } = new List<Mark>();
    }
}
