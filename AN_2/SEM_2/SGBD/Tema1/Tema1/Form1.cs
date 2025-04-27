using System;
using System.Data;
using System.Data.SqlClient;
using System.Text.RegularExpressions;
using System.Windows.Forms;

namespace Tema1
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=CĆTĆLINA;Initial Catalog=formula1_2;Integrated Security=True");
        SqlDataAdapter daO = new SqlDataAdapter();
        SqlDataAdapter daT = new SqlDataAdapter();  //adaptoare de date (pentru ORAS și TARA) care trimit comenzi SQL și aduc rezultate.
        DataSet dsO = new DataSet();
        DataSet dsT = new DataSet();  //dataset-uri în care se stochează rezultatele interogărilor.
        BindingSource bsO = new BindingSource();
        BindingSource bsT = new BindingSource();  //pentru legătura între UI și date (Binding).

        public Form1()
        {
            InitializeComponent();
            gridTara.SelectionChanged += gridTara_SelectionChanged;
            Stergere.Click += Stergere_Click;
            Actualizare.Click += Actualizare_Click;
            Adaugare.Click += Adaugare_Click;
        }

        private void Conectare_Click(object sender, EventArgs e)
        {
            daT.SelectCommand = new SqlCommand("SELECT * FROM TARA", cs);
            dsT.Clear();
            daT.Fill(dsT);
            gridTara.DataSource = dsT.Tables[0];
            bsT.DataSource = dsT.Tables[0];
            bsT.MoveLast();
        }

        private void gridTara_SelectionChanged(object sender, EventArgs e)
        {
            if (gridTara.SelectedRows.Count > 0)
            {
                int id_tara = Convert.ToInt32(gridTara.SelectedRows[0].Cells["id_tara"].Value);
                LoadOrase(id_tara);
            }
        }

        private void LoadOrase(int id_tara)
        {
            try
            {
                string query = "SELECT * FROM ORAS WHERE id_tara = @id_tara";
                daO.SelectCommand = new SqlCommand(query, cs);
                daO.SelectCommand.Parameters.Clear();
                daO.SelectCommand.Parameters.AddWithValue("@id_tara", id_tara);

                dsO.Clear();
                gridOras.DataSource = null;
                daO.Fill(dsO);
                gridOras.DataSource = dsO.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
            }
        }

        private void Adaugare_Click(object sender, EventArgs e)
        {
            try
            {
                if (!ValidareInput()) return;

                int id_tara = Convert.ToInt32(gridTara.SelectedRows[0].Cells["id_tara"].Value);

                daO.InsertCommand = new SqlCommand("INSERT INTO ORAS (id_tara, nume, sofer_provenienta_nume, nr_locuitori) VALUES (@t, @n, @s, @l)", cs);
                daO.InsertCommand.Parameters.Add("@t", SqlDbType.Int).Value = id_tara;
                daO.InsertCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = oras.Text;
                daO.InsertCommand.Parameters.Add("@s", SqlDbType.VarChar).Value = sofer.Text;
                daO.InsertCommand.Parameters.Add("@l", SqlDbType.Int).Value = int.Parse(nrloc.Text);

                cs.Open();
                daO.InsertCommand.ExecuteNonQuery();
                cs.Close();

                MessageBox.Show("Adaugarea s-a realizat cu succes!");
                LoadOrase(id_tara);
                GolireTextBox();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
                cs.Close();
            }
        }

        private void Stergere_Click(object sender, EventArgs e)
        {
            try
            {
                if (gridOras.SelectedRows.Count == 0)
                {
                    MessageBox.Show("Selecteaza un oras pentru a-l sterge!");
                    return;
                }

                int id_oras = Convert.ToInt32(dsO.Tables[0].Rows[gridOras.CurrentCell.RowIndex][0]);

                daO.DeleteCommand = new SqlCommand("DELETE FROM ORAS WHERE id_oras = @id_oras", cs);
                daO.DeleteCommand.Parameters.Add("@id_oras", SqlDbType.Int).Value = id_oras;

                cs.Open();
                daO.DeleteCommand.ExecuteNonQuery();
                cs.Close();

                MessageBox.Show("Stergerea s-a realizat cu succes!");
                int id_tara = Convert.ToInt32(gridTara.SelectedRows[0].Cells["id_tara"].Value);
                LoadOrase(id_tara);
                GolireTextBox();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
                cs.Close();
            }
        }

        private void Actualizare_Click(object sender, EventArgs e)
        {
            try
            {
                if (gridOras.SelectedRows.Count == 0)
                {
                    MessageBox.Show("Selecteaza un oras pentru a-l actualiza!");
                    return;
                }

                if (!ValidareInput()) return;

                int id_oras = Convert.ToInt32(dsO.Tables[0].Rows[gridOras.CurrentCell.RowIndex][0]);

                daO.UpdateCommand = new SqlCommand("UPDATE ORAS SET nume = @n, sofer_provenienta_nume = @s, nr_locuitori = @l WHERE id_oras = @id", cs);
                daO.UpdateCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = oras.Text;
                daO.UpdateCommand.Parameters.Add("@s", SqlDbType.VarChar).Value = sofer.Text;
                daO.UpdateCommand.Parameters.Add("@l", SqlDbType.Int).Value = int.Parse(nrloc.Text);
                daO.UpdateCommand.Parameters.Add("@id", SqlDbType.Int).Value = id_oras;

                cs.Open();
                daO.UpdateCommand.ExecuteNonQuery();
                cs.Close();

                MessageBox.Show("Actualizarea s-a realizat cu succes!");
                int id_tara = Convert.ToInt32(gridTara.SelectedRows[0].Cells["id_tara"].Value);
                LoadOrase(id_tara);
                GolireTextBox();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
                cs.Close();
            }
        }

        private bool ValidareInput()
        {
            if (string.IsNullOrWhiteSpace(oras.Text) || string.IsNullOrWhiteSpace(sofer.Text) || string.IsNullOrWhiteSpace(nrloc.Text))
            {
                MessageBox.Show("Toate campurile trebuie completate!", "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return false;
            }

            if (!Regex.IsMatch(oras.Text, @"^[a-zA-Z\s]+$") || !Regex.IsMatch(sofer.Text, @"^[a-zA-Z\s]+$"))
            {
                MessageBox.Show("Numele orasului si al soferului trebuie sa contina doar litere!", "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return false;
            }

            if (!int.TryParse(nrloc.Text, out int nr) || nr <= 0)
            {
                MessageBox.Show("Numarul de locuitori trebuie sa fie un numar pozitiv!", "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return false;
            }
            if (ExistaOras(oras.Text))
            {
                MessageBox.Show("Orasul exista deja in baza de date!", "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return false;
            }

            return true;
        }

        private bool ExistaOras(string numeOras)
        {
            SqlCommand cmd = new SqlCommand("SELECT COUNT(*) FROM ORAS WHERE nume = @nume", cs);
            cmd.Parameters.AddWithValue("@nume", numeOras);
            cs.Open();
            int count = (int)cmd.ExecuteScalar();
            cs.Close();
            return count > 0;
        }

        public void GolireTextBox()
        {
            oras.Text = "";
            sofer.Text = "";
            nrloc.Text = "";
        }

        private void gridTara_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}
