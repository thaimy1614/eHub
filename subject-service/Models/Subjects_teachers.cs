namespace subject_service.Models
{
    public class Subjects_teachers
    {
        public long subject_id { get; set; }

        public string teacher_id { get; set; }

        public virtual Subject? Subject { get; set; }
    }
}
