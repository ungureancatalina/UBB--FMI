using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Model.mpp.model;

namespace CursaRest.Controllers;

[ApiController]
[Route("api/curse")]
public class CursaController : ControllerBase
{
    private readonly AppDbContext _context;

    public CursaController(AppDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<Cursa>>> GetAll()
    {
        return await _context.Curse.ToListAsync();
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<Cursa>> GetById(int id)
    {
        var cursa = await _context.Curse.FindAsync(id);
        if (cursa == null) return NotFound();
        return cursa;
    }

    [HttpPost]
    public async Task<ActionResult<int>> Create(Cursa cursa)
    {
        _context.Curse.Add(cursa);
        await _context.SaveChangesAsync();
        return CreatedAtAction(nameof(GetById), new { id = cursa.Id }, cursa.Id);
    }

    [HttpPut("{id}")]
    public async Task<IActionResult> Update(int id, Cursa cursa)
    {
        if (id != cursa.Id) return BadRequest();

        var existing = await _context.Curse.FindAsync(id);
        if (existing == null) return NotFound();

        existing.Nume = cursa.Nume;
        existing.CapacitateMinima = cursa.CapacitateMinima;
        existing.CapacitateMaxima = cursa.CapacitateMaxima;

        await _context.SaveChangesAsync();
        return Ok(existing);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(int id)
    {
        var cursa = await _context.Curse.FindAsync(id);
        if (cursa == null) return NotFound();

        _context.Curse.Remove(cursa);
        await _context.SaveChangesAsync();
        return NoContent();
    }
}