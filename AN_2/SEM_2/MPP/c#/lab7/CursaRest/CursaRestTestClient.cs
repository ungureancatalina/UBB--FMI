using System.Net.Http.Json;
using Model.mpp.model;

namespace CursaRest;

public class CursaRestTestClient
{
    private readonly HttpClient _client;
    private readonly string _baseUrl = "http://localhost:8000/api/curse";

    public CursaRestTestClient()
    {
        _client = new HttpClient();
    }

    public async Task RunAllAsync()
    {
        int id = await CreateCursaAsync();
        var cursa = await GetCursaAsync(id);
        if (cursa != null)
        {
            await UpdateCursaAsync(cursa);
            await GetCursaAsync(id);
        }
        await GetAllCurseAsync();
        await DeleteCursaAsync(id);
        Console.WriteLine(" Test complet executat.");
    }
    
    public async Task<int> CreateCursaAsync()
    {
        var toCreate = new Cursa
        {
            Nume = "Cupa Nationala",
            CapacitateMinima = 100,
            CapacitateMaxima = 500
        };

        var response = await _client.PostAsJsonAsync(_baseUrl, toCreate);
        response.EnsureSuccessStatusCode();

        int? newId = await response.Content.ReadFromJsonAsync<int>();
        Console.WriteLine($"Created Cursa id={newId}");
        return newId ?? -1;
    }

    public async Task<Cursa?> GetCursaAsync(int id)
    {
        var response = await _client.GetAsync($"{_baseUrl}/{id}");
        if (!response.IsSuccessStatusCode) return null;

        var cursa = await response.Content.ReadFromJsonAsync<Cursa>();
        Console.WriteLine($"Fetched: {cursa}");
        return cursa;
    }

    public async Task UpdateCursaAsync(Cursa cursa)
    {
        cursa.CapacitateMaxima = 600;
        var response = await _client.PutAsJsonAsync($"{_baseUrl}/{cursa.Id}", cursa);
        Console.WriteLine($"Updated status: {response.StatusCode}");
    }

    public async Task GetAllCurseAsync()
    {
        var curse = await _client.GetFromJsonAsync<List<Cursa>>(_baseUrl);
        Console.WriteLine("All curse:");
        curse?.ForEach(c => Console.WriteLine(c));
    }

    public async Task DeleteCursaAsync(int id)
    {
        var response = await _client.DeleteAsync($"{_baseUrl}/{id}");
        Console.WriteLine($"Deleted id={id} → {response.StatusCode}");
    }
}