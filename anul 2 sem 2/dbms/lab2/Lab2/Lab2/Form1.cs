using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab2
{
    public partial class Form1 : Form
    {
        SqlConnection sqlConnection;
        SqlDataAdapter adapterTable1;
        SqlDataAdapter adapterTable2;
        DataSet dataSet = new DataSet();
        BindingSource bindingSourceTable1 = new BindingSource();
        BindingSource bindingSourceTable2 = new BindingSource();
        public Form1()
        {
            InitializeComponent();
        }



        private void button1_Click(object sender, EventArgs e)
        {
            sqlConnection = new SqlConnection("Data Source=DESKTOP-4JGHNP2; Initial Catalog=CarBusiness; Integrated Security=True");
            adapterTable1 = new SqlDataAdapter("Select * from Cars", sqlConnection);
            adapterTable1.Fill(dataSet, "Cars");



            dataGridView1.DataSource = dataSet.Tables["Cars"];
            bindingSourceTable1.DataSource = dataSet.Tables["Cars"];



            // DataRelation dr = new DataRelation("FK_name", dataSet.Tables["Customer"].Columns["ID"], dataSet.Tables["Orders"].Columns["customerID"]);
            // dataSet.Relations.Add(dr);
            // dataSet.Tables["Customer"].Rows[bindingSourceTable1.Position].GetChildRows



            textBox1.DataBindings.Add("Text", bindingSourceTable1, "Model");



            bindingSourceTable1.MoveNext();
            dataGridView1.ClearSelection();
            dataGridView1.Rows[bindingSourceTable1.Position].Selected = true;



        }



        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            bindingSourceTable1.Position = e.RowIndex;
            dataGridView1.ClearSelection();
            dataGridView1.Rows[bindingSourceTable1.Position].Selected = true;
        }
    }
}
