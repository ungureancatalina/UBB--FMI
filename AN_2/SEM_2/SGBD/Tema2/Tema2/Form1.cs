using System.Configuration;
using System.Data.SqlClient;
using System;
using System.Data;
using System.Drawing;
using System.Windows.Forms;

namespace Tema2
{
    public partial class Form1 : Form
    {
        private TextBox[] textBoxes;
        private string[] childColumns;
        
        // Citirea string-ului de conexiune din App.config
        static string con = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
        SqlConnection cs = new SqlConnection(con);
        
        SqlDataAdapter daParent = new SqlDataAdapter();
        SqlDataAdapter daChild = new SqlDataAdapter();
        DataSet dsParent = new DataSet();
        DataSet dsChild = new DataSet();

        public Form1()
        {
            InitializeComponent();
        }

        // Evenimentul care se declanseaza la incarcarea formularului
         private void Form1_Load(object sender, EventArgs e)
        { 
            string parentQuery = ConfigurationManager.AppSettings["ParentSelectAll"];
            daParent.SelectCommand = new SqlCommand(parentQuery, cs);
            dsParent.Clear();
            daParent.Fill(dsParent);
            dataGridView1.DataSource = dsParent.Tables[0];
        }
         
        // Cand se selecteaza o celula din tabelul parinte
        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            string parentId = dataGridView1.Rows[e.RowIndex].Cells[0].Value.ToString();
            string childQuery = ConfigurationManager.AppSettings["ChildSelectAll"];
            daChild.SelectCommand = new SqlCommand(childQuery.Replace("@ParentId", parentId), cs);

            dsChild.Clear();
            daChild.Fill(dsChild);
            dataGridView2.DataSource = dsChild.Tables[0];
            GenerateControls();
        }
        
        // Cand se selecteaza o celula in tabelul copil
        private void dataGridView2_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex < 0) return;

            DataGridViewRow row = dataGridView2.Rows[e.RowIndex];

            for (int i = 0; i < childColumns.Length; i++)
            {
                string colName = childColumns[i].Trim();
                if (row.Cells[colName] != null)
                {
                    textBoxes[i].Text = row.Cells[colName].Value?.ToString();
                }
            }
        }
        
        // Genereaza controalele (label + textbox) in functie de configurare
        private void GenerateControls()
        {
            string colString = ConfigurationManager.AppSettings["ChildColumnNames"];
            if (string.IsNullOrWhiteSpace(colString))
            {
                MessageBox.Show("Nu s-au găsit coloanele copil în App.config (cheia 'ChildColumnNames')!");
                return;
            }
            childColumns = colString.Split(',');
            textBoxes = new TextBox[childColumns.Length];

            panel2.Controls.Clear();

            for (int i = 0; i < childColumns.Length; i++)
            {
                Label lbl = new Label()
                {
                    Text = childColumns[i].Trim(),
                    Location = new Point(10, i * 30),
                    Width = 100
                };
                TextBox txt = new TextBox()
                {
                    Name = "txt" + childColumns[i].Trim(),
                    Location = new Point(120, i * 30),
                    Width = 160
                };
                panel2.Controls.Add(lbl);
                panel2.Controls.Add(txt);
                textBoxes[i] = txt;
            }
        }

        // Buton pentru adaugare
        private void adaugareButton_Click(object sender, EventArgs e)
        {
            if (!Validate())
            {
                return;
            }
            if (dataGridView1.SelectedRows.Count == 0)
            {
                MessageBox.Show("Selectează un rând din tabela principală (parent)!");
                return;
            }
            string query = ConfigurationManager.AppSettings["Insert"];
            string[] vars = ConfigurationManager.AppSettings["InsertVar"].Split(',');
            string parentId = dataGridView1.SelectedRows[0].Cells[0].Value.ToString();
            string foreignKey = ConfigurationManager.AppSettings["ChildForeignKey"];
            query = query.Replace("@"+ foreignKey, parentId);

            if (textBoxes == null || textBoxes.Length == 0)
            {
                MessageBox.Show("Alege mai întâi un rând din tabelul principal ca să poți adăuga.");
                return;
            }
            
            for (int i = 0; i < vars.Length; i++)
                query = query.Replace(vars[i], textBoxes[i].Text);

            ExecuteQuery(query, "Adaugat cu succes!");
        }
        
        // Buton pentru actualizare
        private void actualizareButton_Click(object sender, EventArgs e)
        {
            if (!Validate())
            {
                return;
            }
            if (dataGridView2.SelectedRows.Count == 0)
            {
                MessageBox.Show("Selectează un rând din tabela principală (parent)!");
                return;
            }

            string query = ConfigurationManager.AppSettings["Update"];
            string[] vars = ConfigurationManager.AppSettings["UpdateVar"].Split(',');
            string childPrimaryKey = ConfigurationManager.AppSettings["ChildPrimaryKey"];
            
            if (textBoxes == null || textBoxes.Length == 0 || childColumns == null)
            {
                MessageBox.Show("Alege mai întâi un rând din tabelul principal ca să poți adăuga.");
                return;
            }
            for (int i = 0; i < vars.Length; i++)
            {
                query = query.Replace(vars[i], textBoxes[i].Text);
            }

            string childId = dataGridView2.SelectedRows[0].Cells[0].Value.ToString();
            query = query.Replace("@" + childPrimaryKey, childId);

            ExecuteQuery(query, "Actualizat cu succes!");
        }

        
        // Buton pentru stergere
        private void stergereButton_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count == 0 || dataGridView2.SelectedRows[0].Cells.Count == 0)
            {
                MessageBox.Show("Selectează un rând valid din tabela detaliu pentru ștergere!");
                return;
            }

            string childPrimaryKey = ConfigurationManager.AppSettings["ChildPrimaryKey"];
            string id = dataGridView2.SelectedRows[0].Cells[0].Value.ToString();

            string query = ConfigurationManager.AppSettings["Delete"].Replace("@" + childPrimaryKey, id);
            ExecuteQuery(query, "Șters cu succes!");
        }

        // Executa o interogare si afiseaza un mesaj
        private void ExecuteQuery(string query, string message)
        {
            try
            {
                cs.Open();
                SqlCommand cmd = new SqlCommand(query, cs);
                cmd.ExecuteNonQuery();
                cs.Close();
                MessageBox.Show(message);
                RefreshChild();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
                cs.Close();
            }
        }

        // Reincarca tabela copil
        private void RefreshChild()
        {
            string parentId = dataGridView1.SelectedRows[0].Cells[0].Value.ToString();
            string foreignKey = ConfigurationManager.AppSettings["ChildForeignKey"];
            string query = ConfigurationManager.AppSettings["ChildSelectAll"].Replace("@ParentId", parentId);

            daChild.SelectCommand = new SqlCommand(query, cs);
            dsChild.Clear();
            daChild.Fill(dsChild);
            dataGridView2.DataSource = dsChild.Tables[0];
            GenerateControls();
        }
        
        // Inchide aplicatia
        private void exitButton_Click(object sender, EventArgs e)
        {
            DialogResult result = MessageBox.Show("Părăsești aplicația?","Exit" , MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (result == DialogResult.Yes)
            {
                Application.Exit();
            }
        }
        
        //Valideaza campurile
        private bool Validate()
        {
            if (childColumns == null || textBoxes == null || textBoxes.Length == 0)
            {
                MessageBox.Show("Controalele nu au fost generate corect.");
                return false;
            }

            for (int i = 0; i < childColumns.Length; i++)
            {
                string col = childColumns[i].Trim();
                TextBox tb = textBoxes[i];
                if (tb == null || string.IsNullOrWhiteSpace(tb.Text))
                {
                    MessageBox.Show($"Câmpul '{col}' nu poate fi gol!");
                    return false;
                }

                // Validări suplimentare în funcție de coloana (exemple):
                if (col.ToLower().Contains("nume"))
                {
                    if (!System.Text.RegularExpressions.Regex.IsMatch(tb.Text, @"^[a-zA-Z\s]+$"))
                    {
                        MessageBox.Show($"Câmpul '{col}' trebuie să conțină doar litere!");
                        return false;
                    }
                }

                if (col.ToLower().Contains("nr") || col.ToLower().Contains("locuitori"))
                {
                    if (!int.TryParse(tb.Text, out int nr) || nr <= 0)
                    {
                        MessageBox.Show($"Câmpul '{col}' trebuie să fie un număr pozitiv!");
                        return false;
                    }
                }
            }

            return true;
        }
    }
}
