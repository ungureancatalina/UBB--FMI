using Microsoft.EntityFrameworkCore;
using Model.mpp.model;

namespace CursaRest
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) {}

        public DbSet<Cursa> Curse { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Cursa>(entity =>
            {
                entity.ToTable("cursa"); 

                entity.HasKey(c => c.Id);
                entity.Property(c => c.Id).HasColumnName("idCursa");
                entity.Property(c => c.Nume).HasColumnName("nume");
                entity.Property(c => c.CapacitateMinima).HasColumnName("capacitate_minima");
                entity.Property(c => c.CapacitateMaxima).HasColumnName("capacitate_maxima");
            });
        }
    }
}