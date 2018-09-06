package directeurGeneral;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.PreparedStatement;

import baseDeDonnées.ConnectionDB;
import javaBeansClass.Article;
import javaBeansClass.Fournisseur;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class ArticleNonVenduController implements Initializable{
	
	@FXML private AnchorPane root;
	
	@FXML private TableView<Article> tableViewDetails;

    @FXML private TableColumn<Article, String> colonneNom;
    @FXML private TableColumn<Article, String> colonneIdRayon;
    @FXML private TableColumn<Article, String> colonneIdCqtegorie;
    @FXML private TableColumn<Article, String> colonneRaisonSociale;
    @FXML private TableColumn<Article, String> colonneCodeBarre;
    @FXML private TableColumn<Article, String> colonneQuantite;
    @FXML private TableColumn<Article, String> colonnePrixQchat;
    @FXML private TableColumn<Article, String> colonnePrixVendu;
    
    @FXML private JFXDatePicker dateA;

    @FXML private JFXDatePicker dateB;
    @FXML private JFXTextField recherch;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ActualiserDonneesQrticleDetailsTableau();
//		selectionMutiple();
		tableViewDetails.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableViewDetails.getSelectionModel().setCellSelectionEnabled(false);
		

	}
	
	// ACTUALISER LES DONNEES SUR TABLEAU
	
	ObservableList<Article> articleList = FXCollections.observableArrayList();
	
		public void ActualiserDonneesQrticleDetailsTableau() {
			
			Connection connexion = ConnectionDB.maConnection();
			
			String requetteIni = "SELECT `nomArticleNom`, `idRayon`, `idCategoriee`, `raisonSociale`, `codeBarre`, qteStock, prixUnitaire, prixAvendre  FROM `Article` WHERE Livrer0nonLivrer1=1"; 
			
			try {
				PreparedStatement pst = (PreparedStatement) connexion.prepareStatement(requetteIni);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					articleList.addAll(new Article(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getDouble(7),rs.getDouble(8)));
				}
				
				colonneNom.setCellValueFactory(new PropertyValueFactory<>("nomArticleNom"));
				colonneIdRayon.setCellValueFactory(new PropertyValueFactory<>("idRayon"));
				colonneIdCqtegorie.setCellValueFactory(new PropertyValueFactory<>("idCategoriee"));
				colonneRaisonSociale.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));
				colonneCodeBarre.setCellValueFactory(new PropertyValueFactory<>("codeBarre"));
				colonneQuantite.setCellValueFactory(new PropertyValueFactory<>("qteStock"));
				colonnePrixQchat.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
				colonnePrixVendu.setCellValueFactory(new PropertyValueFactory<>("prixAvendre"));
				
				tableViewDetails.setItems(articleList);
				
				
			} catch (Exception exActualiserDonneesFournisseurTableau) {
				Logger.getLogger(ArticleNonVenduController.class.getName()).log(Level.SEVERE, null, exActualiserDonneesFournisseurTableau);
			}
		}
		
	//----------------------------------------------------------------------------
	//----------------------------------------------------------------------------
		
		// LISTER PAR DATE
		
		public void listerParDate() {
			
			tableViewDetails.getItems().clear();  // EFFACER LE CONTENU DU TABLEAU AVANT AJOUT
			Connection connexion = ConnectionDB.maConnection();
			
			String requetteI = "SELECT `nomArticleNom`, `idRayon`, `idCategoriee`, `raisonSociale`, `codeBarre`, qteStock, prixUnitaire, prixAvendre  FROM `Article` WHERE dateAjoutee BETWEEN '" + dateA.getValue()+"' AND '"+ dateB.getValue()+"' AND Livrer0nonLivrer1=1  "; 
			
			
			try {
				PreparedStatement pst = (PreparedStatement) connexion.prepareStatement(requetteI);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					articleList.addAll(new Article(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getDouble(7),rs.getDouble(8)));
				}
				
				colonneNom.setCellValueFactory(new PropertyValueFactory<>("nomArticleNom"));
				colonneIdRayon.setCellValueFactory(new PropertyValueFactory<>("idRayon"));
				colonneIdCqtegorie.setCellValueFactory(new PropertyValueFactory<>("idCategoriee"));
				colonneRaisonSociale.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));
				colonneCodeBarre.setCellValueFactory(new PropertyValueFactory<>("codeBarre"));
				colonneQuantite.setCellValueFactory(new PropertyValueFactory<>("qteStock"));
				colonnePrixQchat.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
				colonnePrixVendu.setCellValueFactory(new PropertyValueFactory<>("prixAvendre"));
				
				tableViewDetails.setItems(articleList);
				
				
			} catch (Exception exActualiserDonneesFournisseurTableau) {
				Logger.getLogger(ArticleNonVenduController.class.getName()).log(Level.SEVERE, null, exActualiserDonneesFournisseurTableau);
			} 
			} 
		
	
	//====================
		
		@SuppressWarnings("unchecked")
		public void selectionMutiple() {
			
			
			
			for(TablePosition<Article, ?> pos : tableViewDetails.getSelectionModel().getSelectedCells()) {
				TableColumn<Article, ?> colum = pos.getTableColumn();
				ObservableValue<?> obs = colum.getCellObservableValue(pos.getRow());
				Object value = obs.getValue();
				
				System.out.println(value);
			}
		}
//==================================================================================================================		
		//----------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------
				
			final ObservableList<Article> data = FXCollections.observableArrayList();
			
			public void rechercheFiltrs(KeyEvent ke) {
				// RECHERCHE - RECHERCHE - RECHERCHE - RECHERCHE - RECHERCHE - RECHERCHE
				
					FilteredList<Article> filtrFournisseur = new FilteredList<>(data, e -> true);
					
					recherch.setOnKeyReleased(e -> {
						recherch.textProperty().addListener((observableValue, oldValue, newValue) ->{
							filtrFournisseur.setPredicate((Predicate<? super Article>) qrticleReserch->{
								if(newValue == null || newValue.isEmpty()) {
								return true;
								}
								String lowerCaseFiltrer = newValue.toLowerCase();
//								if(fourniseurReserch.getID().contains(newValue)){
//								} else
								if (qrticleReserch.getNomArticleNom().toLowerCase().contains(lowerCaseFiltrer)) {
									return true;
								} else if( qrticleReserch.getCodeBarre().toLowerCase().contains(lowerCaseFiltrer) ) {
									return true;
								}
								return false;
							});
						});
						SortedList<Article>  sortData = new SortedList<>(filtrFournisseur);
						sortData.comparatorProperty().bind(tableViewDetails.comparatorProperty());
						tableViewDetails.setItems(sortData);
					});		
			}
				
		
		
}


