using System.ComponentModel.DataAnnotations.Schema;

namespace subject_service.Models
{
    public partial class Mark
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long id { get; set; }

        public float? score_average { get; set; }

        public float weight {  get; set; }

        public string student_id { get; set; }

        public long subject_id { get; set; }

        public long school_year_id { get; set; }
        
        public long semester_id { get; set; }

        public virtual Subject? Subject { get; set; }

        public virtual ICollection<ComponentPoint> ComponentPoints { get; set; } = new List<ComponentPoint>();
    }
}
