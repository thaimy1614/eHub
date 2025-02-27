using Microsoft.EntityFrameworkCore;
using subject_service.Models;
using System.Diagnostics;

namespace subject_service.DbMigrator
{
    public class SubjectServiceDbContext : DbContext
    {
        public SubjectServiceDbContext(DbContextOptions<SubjectServiceDbContext> options) : base(options) { }

        public DbSet<Subject> subjects { get; set; }
        public DbSet<Mark> marks { get; set; }
        public DbSet<ComponentPoint> component_points { get; set; }
        public DbSet<Subjects_teachers> subjects_teachers { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Subject>(entity =>
            {
                entity.HasKey(s => s.id);
                entity.Property(s => s.name).IsRequired().HasMaxLength(100);
                entity.Property(s => s.grade_id).IsRequired();
                entity.Property(s => s.total_sessions);
                entity.Property(s => s.is_deleted).IsRequired();
                entity.Property(s => s.school_year_id).IsRequired();
            });

            modelBuilder.Entity<Mark>(entity =>
            {
                entity.HasKey(s => s.id);
                entity.Property(s => s.score_average);
                entity.Property(s => s.weight);
                entity.Property(s => s.student_id).IsRequired().HasMaxLength(255);
                entity.Property(s => s.subject_id).IsRequired();
                entity.Property(s => s.school_year_id).IsRequired();
                entity.Property(s => s.semester_id).IsRequired();

                entity.HasOne(g => g.Subject)
                      .WithMany(s => s.Marks)
                      .HasForeignKey(g => g.subject_id)
                      .OnDelete(DeleteBehavior.ClientNoAction);
            });

            modelBuilder.Entity<ComponentPoint>(entity =>
            {
                entity.HasKey(s => s.id);
                entity.Property(s => s.exam_type).IsRequired().HasMaxLength(50);
                entity.Property(s => s.weight).IsRequired();
                entity.Property(s => s.score).IsRequired();
                entity.Property(s => s.column_order).IsRequired();
                entity.Property(s => s.semester_type).IsRequired().HasMaxLength(50);
                entity.Property(s => s.is_update);

                entity.HasOne(g => g.Mark)
                      .WithMany(s => s.ComponentPoints)
                      .HasForeignKey(g => g.mark_id)
                      .OnDelete(DeleteBehavior.ClientSetNull);
            });
            
            modelBuilder.Entity<Subjects_teachers>(entity =>
            {
                entity.HasKey(s => new { s.subject_id, s.teacher_id});
                entity.Property(s => s.subject_id).IsRequired();
                entity.Property(s => s.teacher_id).IsRequired().HasMaxLength(255);

                entity.HasOne(g => g.Subject)
                      .WithMany(s => s.Subjects_teachers)
                      .HasForeignKey(g => g.subject_id)
                      .OnDelete(DeleteBehavior.ClientSetNull);
            });
        }
    }
}
