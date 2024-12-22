
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;


public class ControleAluno {
    
    Aluno aluno = new Aluno();
    
    private Connection conexao = null;
    private Statement st  = null;
    java.sql.ResultSet rs;
    
    public void Conecta(){
     try{
        String servername="localhost:3307";
        String usuario="root";
        String senha="";
        String banco="bdaluno";
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class.forName(driverName);
        String url = "jdbc:mysql://" + servername + "/" + banco+"?useTimezone=true&serverTimezone=UTC"; // a JDBC url
        conexao = DriverManager.getConnection(url,usuario,senha);
        st = conexao.createStatement();
     }
     catch(ClassNotFoundException e){	     
	   System.out.println("Driver não encontrado!");
     }
     catch(Exception e){
           System.out.println("Erro ao conectar Banco!");
     }
     }
    
    public void Cadastrar(int rgm,String nm,double n1,double n2){
        aluno.setRgm(rgm);
        aluno.setNome(nm);
        aluno.setNota1(n1);
        aluno.setNota2(n2);
        try{
          PreparedStatement sql;
          sql=conexao.prepareStatement("Insert into alunos (numrgm,nome_aluno,nota1,nota2) values (?,?,?,?)");
          sql.setInt(1, aluno.getRgm());
          sql.setString(2, aluno.getNome());
          sql.setDouble(3,aluno.getNota1());
          sql.setDouble(4, aluno.getNota2());
          int valor=sql.executeUpdate();
          if (valor==1){
              JOptionPane.showMessageDialog(null,"Cadastro realizado!");
          }else{
              JOptionPane.showMessageDialog(null,"Erro de Cadastro!");
          }
          
        }
        catch(Exception e){
            System.out.println("Erro sql!");
        }
    } 
    public void Listar(){
        PreparedStatement sql;
        
        try{
           sql= conexao.prepareStatement("Select * from alunos");
           rs = sql.executeQuery();
           while (rs.next()){
               System.out.println(rs.getString("numrgm")+" "+
                       rs.getString("nome_aluno")+" "+
                       rs.getString("nota1")+" "+
                       rs.getString("nota2"));
           }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro sql!");
        }
       
    }
    public String Consultar(int rgm){
        String dados="";
        PreparedStatement sql;
        try{
            sql=conexao.prepareStatement("select * from alunos where numrgm = ?");
            sql.setInt(1,rgm);
            rs = sql.executeQuery();
            if(rs.next()){
                dados=(rs.getString("numrgm")+";"+rs.getString("nome_aluno")+";"+rs.getString("nota1")+";"+
                        rs.getString("nota2"));
               
            }
            else{
                JOptionPane.showMessageDialog(null,"Numero de rgm não encontrado!");
                
            }
           
        }
        catch(Exception e){
             JOptionPane.showMessageDialog(null,"Erro sql!");
        }
        return dados;
    }
    public void Excluir(int rgm){
        PreparedStatement sql;
        try{
           sql=conexao.prepareStatement("delete from alunos where numrgm= ?");
           sql.setInt(1, rgm);
           int verifica=sql.executeUpdate();
           if (verifica==1){
               JOptionPane.showMessageDialog(null,"Registro Excluido!");
           }
           else{
               JOptionPane.showMessageDialog(null,"Numero de rgm não encontrado!");
           }
           
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro sql!");
        }
        
    }
    public void Alterar(int rgm, String nm, double n1,double n2){
        PreparedStatement sql;
        try{
            sql=conexao.prepareStatement("Update alunos set nome_aluno='"+nm+"',nota1='"+n1+
                    "',nota2='"+n2+"' where numrgm="+rgm);
            int verifica=sql.executeUpdate();
            if (verifica==1){
                JOptionPane.showMessageDialog(null,"Registro Alterado!");
            }
            else{
                JOptionPane.showMessageDialog(null,"Numero de rgm não encontrado!");
            }
        }
        catch(Exception e){
             JOptionPane.showMessageDialog(null,"Erro sql!");
        }
    }
    public double CalcMedia(double n1,double n2){
        return (n1+n2)/2;
    }
        
}
