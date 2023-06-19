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

namespace Lab1
{
    public partial class Form1 : Form
    {
        SqlConnection connection;
        SqlDataAdapter brandAdapter, carsAdapter;
        DataSet ds;
        BindingSource brandBindingSource, carsBindingSource;

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            connection = new SqlConnection(@"Data source=IANIS\SQLEXPRESS; " +
                                           " Initial Catalog=CarBusiness; Integrated Security = SSPI");
            ds = new DataSet();
            brandAdapter = new SqlDataAdapter("SELECT * FROM CarBrands", connection);
            carsAdapter = new SqlDataAdapter("SELECT * FROM Cars", connection);

            brandAdapter.Fill(ds, "CarBrands");
            carsAdapter.Fill(ds, "Cars");

            DataRelation dr = new DataRelation("FK_Cars_CarBrands", ds.Tables["CarBrands"].Columns["BrandID"],
                ds.Tables["Cars"].Columns["BId"]);
            ds.Relations.Add(dr);

            brandBindingSource = new BindingSource();
            brandBindingSource.DataSource = ds;
            brandBindingSource.DataMember = "CarBrands" ;

            carsBindingSource = new BindingSource();
            carsBindingSource.DataSource = brandBindingSource;
            carsBindingSource.DataMember = "FK_Cars_CarBrands";

            ParentTable.DataSource = brandBindingSource;
            ChildTable.DataSource= carsBindingSource;
            ;
        }
    }
}