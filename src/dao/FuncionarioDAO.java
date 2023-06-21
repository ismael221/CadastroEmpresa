/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import beans.Empresa;
import beans.Funcionario;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class FuncionarioDAO {
     private Conexao conexao;
    private Connection conn;
    
    public FuncionarioDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Funcionario funcionario){
        String sql = "INSERT INTO funcionario(nomefunc,empresaid,admissao)  VALUES"+"(?,?,?)";
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

       
        try{
            PreparedStatement  stmt = this.conn.prepareStatement(sql);
            stmt.setString(1,funcionario.getNomefunc());
            stmt.setInt(2,funcionario.getEmpresaid().getId());
            stmt.setString(3, sdf.format(funcionario.getDataAdmissao()));
            stmt.execute();
           
        }catch(Exception e){
            System.out.println("Erro ao inserir funcionario: "+ e.getMessage());    
        }
                
    }
    
    public void editar (Funcionario funcionario){
       String sql = "UPDATE funcionario SET  nomefunc=?,empresaid=? WHERE id =?";
       try {
          //esse trecho é igual ao método inserir
          PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_UPDATABLE);
          //Setando os parâmetros
          stmt.setString(1, funcionario.getNomefunc());
          stmt.setInt(2, funcionario.getEmpresaid().getId());
          stmt.setInt(3, funcionario.getId());
          //Executando a query
          stmt.execute();
          //tratando o erro, caso ele ocorra
      } catch (Exception e) {
          System.out.println("Erro ao editar empresa: " + e.getMessage());
      }
   }
   
    public void excluir(int id){
       String sql = "DELETE FROM funcionario WHERE  id = ?";
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
