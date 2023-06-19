using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Configuration;

namespace WindowsFormsApp1
{
    public partial class Form1 : Form
    {
        SqlConnection connection;
        SqlDataAdapter brandAdapter, carsAdapter;
        DataSet ds;
        BindingSource brandBindingSource, carsBindingSource;
        SqlCommandBuilder sqlCommandBuilder;


        private string getConnectionString()
        {
            return ConfigurationManager.ConnectionStrings["connectionString"].ConnectionString.ToString();
        }

        private string getParentTable()
        {
            return ConfigurationManager.AppSettings.Get("parentTable");
        }
        private string getParentTablePrimaryKey()
        {
            return ConfigurationManager.AppSettings.Get("parentPrimaryKey");
        }
        private string getChildTable()
        {
            return ConfigurationManager.AppSettings.Get("childTable");
        }
        private string getChildTableForeignKey()
        {
            return ConfigurationManager.AppSettings.Get("childForeignKey");
        }
        private string getParentQuery()
        {
            return ConfigurationManager.AppSettings.Get("parentQuery");
        }
        private string getChildQuery()
        {
            return ConfigurationManager.AppSettings.Get("childQuery");
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try { carsAdapter.Update(ds, getChildTable());
                MessageBox.Show("Changes saved!");

            }catch(Exception exception) { MessageBox.Show(exception.Message); }
            
        }

        

        private void Form1_Load(object sender, EventArgs e)
        {
            connection = new SqlConnection( getConnectionString() 
                                        );
            ds = new DataSet();
            brandAdapter = new SqlDataAdapter(getParentQuery(), connection);
            carsAdapter = new SqlDataAdapter(getChildQuery(), connection);

            sqlCommandBuilder = new SqlCommandBuilder(carsAdapter);
            brandAdapter.Fill(ds, getParentTable());
            carsAdapter.Fill(ds, getChildTable());

            DataRelation dr = new DataRelation("FK_Cars_CarBrands", ds.Tables[getParentTable()].Columns[getParentTablePrimaryKey()],
                ds.Tables[getChildTable()].Columns[getChildTableForeignKey()]);
            ds.Relations.Add(dr);

            brandBindingSource = new BindingSource();
            brandBindingSource.DataSource = ds;
            brandBindingSource.DataMember = getParentTable();

            carsBindingSource = new BindingSource();
            carsBindingSource.DataSource = brandBindingSource;
            carsBindingSource.DataMember = "FK_Cars_CarBrands";

            ParentTable.DataSource = brandBindingSource;
            ChildTable.DataSource = carsBindingSource;
        }

        public Form1()
        {
            InitializeComponent();
        }
    }
}