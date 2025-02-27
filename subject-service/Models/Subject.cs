using subject_service.Models;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace subject_service.Models
{
    public partial class Subject
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long id { get; set; }

        public string name { get; set; }

        public long grade_id { get; set; }

        public int? total_sessions { get; set; }

        public bool is_deleted { get; set; } = false;

        public long school_year_id { get; set; }

        public int total_teacher
        {
            get
            {
                if (Subjects_teachers.Count > 0)
                {
                    return Subjects_teachers.Count;
                }
                return 0;
            }
        }

        public virtual ICollection<Mark> Marks { get; set; } = new List<Mark>();

        public virtual ICollection<Subjects_teachers> Subjects_teachers { get; set; } = new List<Subjects_teachers>();
    }
}
