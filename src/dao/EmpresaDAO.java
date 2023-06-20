
package dao;

import beans.Empresa;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class EmpresaDAO {
    private Conexao conexao;
    private Connection conn;
    
    public EmpresaDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Empresa empresa){
        String sql = "INSERT INTO empresa(nomeempresa,areaatuacao)  VALUES"+"(?,?)";
       
        try{
            PreparedStatement  stmt = this.conn.prepareStatement(sql);
            stmt.setString(1,empresa.getNomeempresa());
            stmt.setString(2,empresa.getAreaatuacao());
            stmt.execute();
            System.out.println("Inserido");
        }catch(Exception e){
            System.out.println("Erro ao inserir empresa: "+ e.getMessage());    
        }
                
    }
    
    public Empresa getEmpresa(int id){
        String sql = "SELECT * FROM empresa WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            
            Empresa empresa = new Empresa();
            rs.next();
            empresa.setId(id);
            empresa.setNomeempresa(rs.getString("nomeempresa"));
            empresa.setAreaatuacao(rs.getString("areaatuacao"));
            
            return empresa;
        }catch(Exception e){
            System.out.println("Erro: "+e.getMessage());
            return null;
        }
    }
    
   public void editar (Empresa empresa){
       String sql = "UPDATE empresa SET  nomeempresa=?,areaatuacao=? WHERE id =?";
       try {
          //esse trecho é igual ao método inserir
          PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_UPDATABLE);
          //Setando os parâmetros
          stmt.setString(1, empresa.getNomeempresa());
          stmt.setString(2, empresa.getAreaatuacao());
          stmt.setInt(3, empresa.getId());
          //Executando a query
          stmt.execute();
          //tratando o erro, caso ele ocorra
      } catch (Exception e) {
          System.out.println("Erro ao editar empresa: " + e.getMessage());
      }
   }
   
   public Empresa getEmpresaNome(String nomeempresa){
       String sql = "SELECT * FROM empresa WHERE nomeempresa LIKE ?";
       
           try {        
            PreparedStatement stmt = this.conn.prepareStatement(sql);
          
            stmt.setString(1, nomeempresa);
        
            ResultSet rs = stmt.executeQuery();
      
            Empresa empresa = new Empresa();
            rs.next(); 
            
            empresa.setId(rs.getInt("id"));
            empresa.setNomeempresa(nomeempresa);
            empresa.setNomeempresa(rs.getString("nomeempresa"));
            empresa.setAreaatuacao(rs.getString("areaatuacao"));    
          
            return empresa;
          
          //tratando o erro, caso ele ocorra
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
            return null;
        }
   }
   
   public List<Empresa> getEmpresa(String nomeempresa){
       String sql = "SELECT * FROM empresa WHERE nomeempresa LIKE ?";
         
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString (1,"%"+nomeempresa+"%");
            ResultSet rs = stmt.executeQuery();            
            
            List<Empresa> listaEmpresas = new ArrayList<>();
            
            while (rs.next()) { //.next retorna verdadeiro caso exista uma próxima posição dentro do array
                Empresa empresa = new Empresa();
                
                empresa.setId(rs.getInt("id"));
                empresa.setNomeempresa(rs.getString("nomeempresa"));
                empresa.setAreaatuacao(rs.getString("areaatuacao"));
                
                listaEmpresas.add(empresa);    
            }
            return listaEmpresas;
            
            //Se o método entrar no "Catch" quer dizer que não encontrou nenhuma empresa, então damos um "return null"
        } catch (Exception e) {
            return null;
        }
   }
   public List<Empresa> getEmpresaPorId(int id1,int id2){
           String sql = "SELECT * FROM empresa WHERE id >= ? AND id <= ?;"; 
            try {
          PreparedStatement stmt = this.conn.prepareStatement(sql);
                      
          stmt.setInt(1,id1);
          stmt.setInt(2,id2 );
          ResultSet rs = stmt.executeQuery();            
          
          List<Empresa> listaEmpresas = new ArrayList<>();
          //percorrer o resultSet e salvar as informações dentro de uma variável "Empresa"
          //Depois salva essa variavel dentro da lista
          
          //Estrutura de repetição While
          while (rs.next()) { //.next retorna verdadeiro caso exista uma próxima posição dentro do array
              Empresa empresa = new Empresa();
              //Salvar dentro da variavel empresa, as informações            
              empresa.setId(rs.getInt("id"));
              empresa.setNomeempresa(rs.getString("nomeempresa"));
              empresa.setAreaatuacao(rs.getString("areaatuacao"));
              //Adicionando os elementos na lista criada
              listaEmpresas.add(empresa);
          }
          //Após finalizar o while, o retorno será a listaEmpresas, onde cada posição é um registro do banco de dados
          return listaEmpresas;
          
          //Se o método entrar no "Catch" quer dizer que não encontrou nenhuma empresa, então damos um "return null"
      } catch (Exception e) {
          return null;
      }
   }
   
   public void excluir(int id){
       String sql = "DELETE FROM empresa WHERE  id = ?";
          try {
          //esse trecho é igual ao método editar e inserir
          PreparedStatement stmt = this.conn.prepareStatement(sql);
          stmt.setInt(1, id);
          
          //Executando a query
          stmt.execute();
          //tratando o erro, caso ele ocorra
      } catch (Exception e) {
          System.out.println("Erro ao excluir empresa: " + e.getMessage());
      }
   }
}
