using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace subject_service.Migrations
{
    /// <inheritdoc />
    public partial class initV2 : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<long>(
                name: "school_year_id",
                table: "subjects",
                type: "bigint",
                nullable: false,
                defaultValue: 0L);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "school_year_id",
                table: "subjects");
        }
    }
}
