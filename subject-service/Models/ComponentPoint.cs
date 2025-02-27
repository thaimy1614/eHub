using System.ComponentModel.DataAnnotations.Schema;

namespace subject_service.Models
{
    public partial class ComponentPoint
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long id { get; set; }

        public string exam_type { get; set; }

        public float weight { get; set; }

        public float score { get; set; }

        public int column_order { get; set; }

        public string semester_type {  get; set; }

        public bool is_update { get; set; } = false;

        public long mark_id { get; set; }

        public virtual Mark? Mark { get; set; }
    }
}
