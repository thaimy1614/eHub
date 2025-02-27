namespace subject_service.HttpApi
{
    public class ApiResponse <T>
    {
        public string Code { get; set; } 
        public T Result { get; set; } 
    }
}
