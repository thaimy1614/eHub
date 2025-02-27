using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace subject_service.Migrations
{
    /// <inheritdoc />
    public partial class initV1 : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "subjects",
                columns: table => new
                {
                    id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    name = table.Column<string>(type: "character varying(100)", maxLength: 100, nullable: false),
                    grade_id = table.Column<long>(type: "bigint", nullable: false),
                    total_sessions = table.Column<int>(type: "integer", nullable: true),
                    is_deleted = table.Column<bool>(type: "boolean", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_subjects", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "marks",
                columns: table => new
                {
                    id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    score_average = table.Column<float>(type: "real", nullable: true),
                    weight = table.Column<float>(type: "real", nullable: false),
                    student_id = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: false),
                    subject_id = table.Column<long>(type: "bigint", nullable: false),
                    school_year_id = table.Column<long>(type: "bigint", nullable: false),
                    semester_id = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_marks", x => x.id);
                    table.ForeignKey(
                        name: "FK_marks_subjects_subject_id",
                        column: x => x.subject_id,
                        principalTable: "subjects",
                        principalColumn: "id");
                });

            migrationBuilder.CreateTable(
                name: "subjects_teachers",
                columns: table => new
                {
                    subject_id = table.Column<long>(type: "bigint", nullable: false),
                    teacher_id = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_subjects_teachers", x => new { x.subject_id, x.teacher_id });
                    table.ForeignKey(
                        name: "FK_subjects_teachers_subjects_subject_id",
                        column: x => x.subject_id,
                        principalTable: "subjects",
                        principalColumn: "id");
                });

            migrationBuilder.CreateTable(
                name: "component_points",
                columns: table => new
                {
                    id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    exam_type = table.Column<string>(type: "character varying(50)", maxLength: 50, nullable: false),
                    weight = table.Column<float>(type: "real", nullable: false),
                    score = table.Column<float>(type: "real", nullable: false),
                    column_order = table.Column<int>(type: "integer", nullable: false),
                    semester_type = table.Column<string>(type: "character varying(50)", maxLength: 50, nullable: false),
                    is_update = table.Column<bool>(type: "boolean", nullable: false),
                    mark_id = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_component_points", x => x.id);
                    table.ForeignKey(
                        name: "FK_component_points_marks_mark_id",
                        column: x => x.mark_id,
                        principalTable: "marks",
                        principalColumn: "id");
                });

            migrationBuilder.CreateIndex(
                name: "IX_component_points_mark_id",
                table: "component_points",
                column: "mark_id");

            migrationBuilder.CreateIndex(
                name: "IX_marks_subject_id",
                table: "marks",
                column: "subject_id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "component_points");

            migrationBuilder.DropTable(
                name: "subjects_teachers");

            migrationBuilder.DropTable(
                name: "marks");

            migrationBuilder.DropTable(
                name: "subjects");
        }
    }
}
