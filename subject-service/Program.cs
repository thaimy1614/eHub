using AutoMapper;
using Microsoft.EntityFrameworkCore;
using Microsoft.OpenApi.Any;
using Microsoft.OpenApi.Models;
using subject_service.DbMigrator;
using subject_service.HttpApi;
using subject_service.Repository.Implementations;
using subject_service.Repository.Interfaces;
using subject_service.Services.Implementations;
using subject_service.Services.Interfaces;

var builder = WebApplication.CreateBuilder(args);

// Register services
builder.Services.AddDbContext<SubjectServiceDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();

// Add Scope
builder.Services.AddScoped<ISubjectRepository, SubjectRepository>();
builder.Services.AddScoped<IMarkRepository, MarkRepository>();
builder.Services.AddScoped<IComponentPointRepository, ComponentPointRepository>();
builder.Services.AddScoped<ISubjectTeacherRepository, SubjectTeacherRepository>();

builder.Services.AddScoped<ISubjectService, SubjectService>();
builder.Services.AddScoped<IMarkService, MarkService>();
builder.Services.AddScoped<IComponentPointService, ComponentPointService>();
builder.Services.AddScoped<ISubjectTeacherService, SubjectTeacherService>();

builder.Services.AddAutoMapper(typeof(Program).Assembly);


builder.Services.AddSwaggerGen(options =>
{
    options.SwaggerDoc("v1", new OpenApiInfo { Title = "Subject Service", Version = "v1" });
    options.AddServer(new OpenApiServer
        {
            Url = "http://localhost:8888/api",
            Description = "API Gateway"
        });
    options.MapType<ApiResponse<object>>(() => new OpenApiSchema
    {
        Properties = new Dictionary<string, OpenApiSchema>
        {
            ["code"] = new OpenApiSchema { Type = "string", Example = new OpenApiString("1000") },
            ["result"] = new OpenApiSchema { Type = "object" }
        }
    });
});

var app = builder.Build();
    
app.UseSwagger(c =>
{
    c.RouteTemplate = "subject/swagger/{documentName}/swagger.json";
});

app.UseSwaggerUI(c =>
{
    c.SwaggerEndpoint("/subject/swagger/v1/swagger.json", "Subject Service API v1");
    c.RoutePrefix = "subject/swagger";
});

app.UseRouting();
app.UseEndpoints(endpoints =>
{
    endpoints.MapControllers();
});

app.Run();
