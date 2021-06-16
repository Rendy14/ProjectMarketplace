package MarketPlace;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LibraryClass {

protected static void menuawal() {
	Scanner scan = new Scanner(System.in);
	boolean lanjut = false;
	while(!lanjut){
		System.out.println("\n========================MENU UTAMA========================");
		
		System.out.println("\n1.\t Penjual");
		System.out.println("2.\t Pembeli");
		System.out.print("\nSilahkan Pilih Peran Anda : ");
		String peran = scan.next();
		
		switch (peran) {
		case "1" :
			penjual();
			break;
			
		case "2" :
			pembeli();
			break;
			
		default :
			System.out.println("\nPilihan tidak tersedia, silahkan pilih pilihan yang ada!\n");
		}
		lanjut = peran.equals("1") || peran.equals("2");
	}
	scan.close();
	}
	
protected static void penjual() {
		Scanner scanPenjual = new Scanner(System.in);
		
		boolean lanjut = false;
		
		
			System.out.println("\n=======================MENU PENJUAL=======================");
			
			System.out.println("\n!!!Menu ini hanya untuk pemilik toko!!!!\n");
			
			while(!lanjut){
			System.out.println("1.\tLanjutkan");
			System.out.println("2.\tkembali");
			System.out.print("Silahkan pilih : ");
			String pilih = scanPenjual.next();
			
			switch (pilih) {
			case "1" :
				logIn();
				break;
		
			case "2" :
				menuawal();
				break;
				
			default :
				System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
				}
			lanjut = pilih.equals("1") || pilih.equals("2");
			} 	scanPenjual.close();
	}

protected static void pembeli() {
	Scanner scanPembeli = new Scanner(System.in);
		
	System.out.println("=======================MENU PEMBELI=======================\n\n");
	daftarBarang();
	System.out.println("========================================================================");
	
	boolean lanjut = false;
	
	while(!lanjut){
	System.out.println("1.\tPesan Barang");
	System.out.println("2.\tkembali");
	System.out.print("Silahkan pilih : ");
	String pilih = scanPembeli.next();
	
	switch (pilih) {
	case "1" :
		pesanBarang();;
		break;

	case "2" :
		menuawal();
		break;
		
	default :
		System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
		}
	lanjut = pilih.equals("1") || pilih.equals("2");
	} scanPembeli.close();
	}

protected static void logIn() {
	Scanner scanLogIn = new Scanner(System.in);
	System.out.println("\n=================Silahkan isi untuk Login=================\n");
	
	System.out.print("Masukan Username : ");
	String user = scanLogIn.nextLine();
	System.out.print("Masukan Password : ");
	String pass = scanLogIn.nextLine();
		
	String username = "Analog";
	String password = "12345";
	String dbUrl = "jdbc:mysql://localhost:3306/finalp";
	String query = "select username,password from login where username = '" + user + "' and password ='" + pass + "'";
		
		try (
				Connection con = DriverManager.getConnection(dbUrl,username,password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
		){
			if (rs.next()) {
				MenuPenjual();
				} else {
					System.out.println("username atau password salah !!");
					penjual();
				}
			con.close();
				
		}catch (SQLException e ) {
			System.out.println("something wrong!!");
		} scanLogIn.close();
		
	}

protected static void daftarpesanan() {
	Scanner scanDaftarPesanan = new Scanner(System.in);
	
	System.out.println("\n========================DAFTAR PESANAN===========================\n");
	System.out.println("=========================================================================================================");
	System.out.print("| id\t");
	System.out.print("| Nama Pembeli\t\t");
	System.out.print("| id Barang\t");
	System.out.print("|\tno hp\t");
	System.out.print("| Alamat Pembeli\t\t");
	System.out.println("|jumlah pesanan\t|");
	System.out.println("=========================================================================================================");
	
	String username = "Analog";
	String password = "12345";
	String dbUrl = "jdbc:mysql://localhost:3306/finalp";
	String query = "select * from pesanan ";
	try (
			Connection con = DriverManager.getConnection(dbUrl,username,password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
	){
		while (rs.next()) {
			System.out.printf("| %s\t",rs.getString("id_Pembeli"));
			System.out.printf("| %s\t\t",rs.getString("NamaPembeli"));
			System.out.printf("|\t%s\t",rs.getString("id_barang"));
			System.out.printf("|  %s\t",rs.getString("nohp"));
			System.out.printf("| %s   \t",rs.getString("alamat"));
			System.out.printf("|\t%s\t|\n",rs.getString("jumlahpesanan"));
			} System.out.println("=========================================================================================================");
			con.close();
	}catch (SQLException e ) {
		System.out.println("something wrong!!");
	}
	
		boolean lanjut = false;
	
		while(!lanjut){
		System.out.println("\n1.\tHapus Pesanan");
		System.out.println("2.\tkembali");
		System.out.print("Silahkan pilih : ");
		String pilih = scanDaftarPesanan.next();
		switch (pilih) {
		case "1" :
			System.out.print("id Pembeli : ");
			String napem = scanDaftarPesanan.next();
			boolean cekidpembeli = false;
			try (
					Connection con = DriverManager.getConnection(dbUrl,username,password);
					Statement st2 = con.createStatement();
					ResultSet rs2 = st2.executeQuery("select id_pembeli from pesanan where id_pembeli =" + napem );
			){		if(rs2.next()) {
					cekidpembeli = true;
					} else {
						System.out.println("ID pembeli tidak ditemukan !!");
						con.close();
						daftarpesanan();
					} con.close();
			}catch (SQLException e ) {
				System.out.println("something wrong!!");
				daftarpesanan();
			}
			
			if (cekidpembeli) {
			try (
					Connection con = DriverManager.getConnection(dbUrl,username,password);
					PreparedStatement st3 = con.prepareStatement("delete from pesanan where id_Pembeli = " + napem);
				){	
						st3.execute();
						System.out.println("\ndata berhasil dihapus");
						con.close();
						daftarpesanan();
						System.out.println("=================================================================");
			}
				catch (SQLException e ) {
					System.out.println("something wrong!!");
					daftarpesanan();
				}
			}
			break;
			
		case "2" :
			MenuPenjual();
			break;
			
		default :
			System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
			}
		lanjut = pilih.equals("1") || pilih.equals("2");
		} 	scanDaftarPesanan.close();
}

protected static void daftarBarang() {
	System.out.println("\n=========================DAFTAR BARANG===========================\n");
	System.out.println("=================================================================");
	System.out.print("|id\t");
	System.out.print("|  Nama Barang\t");
	System.out.print("|Harga\t");
	System.out.print("| merek barang\t|");
	System.out.println("\n=================================================================");
	
	
	String username = "Analog";
	String password = "12345";
	String dbUrl = "jdbc:mysql://localhost:3306/finalp";
	String query = "select * from barang ";
	try (
			Connection con = DriverManager.getConnection(dbUrl,username,password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
	){
		while (rs.next()) {
			System.out.printf("|%s\t",rs.getString("id_barang"));
			System.out.printf("|  %s\t",rs.getString("namaBarng"));
			System.out.printf("|%s\t",rs.getString("harga"));
			System.out.printf("| %s   \t|\n",rs.getString("merek"));
		} con.close();
	}catch (SQLException e ) {
		System.out.println("something wrong!!");
	} 
		
	}

protected static void tambahBarang() {
	System.out.println("\n======================TAMBAH BARANG=======================\n");
	Scanner scanTambah = new Scanner(System.in);
	List<String> ar = new ArrayList<>(); 
	
	String username = "Analog";
	String password = "12345";
	String dbUrl = "jdbc:mysql://localhost:3306/finalp";
	String query = "insert into barang value (";
	int i = 1;
	boolean saveid = false;
	
	System.out.print("Nama Barang : ");
	String nama = scanTambah.nextLine();
	ar.add(nama);
	System.out.print("harga Barang (angka (Rp)) : ");
	String harga = scanTambah.nextLine();
	ar.add(harga);
	System.out.print("merek Barang : ");
	String merek = scanTambah.nextLine();
	ar.add(merek);
	
	System.out.println("\nData yang anda masukan adalah : " + ar);
	
	try (
			Connection con = DriverManager.getConnection(dbUrl,username,password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select id_barang from barang ");
	){
		while (rs.next()) {
			i++;
		}
		saveid = true;
		con.close();
	}catch (SQLException e ) {
		System.out.println("something wrong!!");
	} 
	
	boolean lanjut = false;
	
	if (saveid) {
	while(!lanjut){
		System.out.println("\n1.\tLanjutkan");
		System.out.println("2.\tBatalkan");
		System.out.print("Silahkan pilih : ");
		String pilih = scanTambah.nextLine();
		
		switch (pilih) {
		case "1" :
			try (
					Connection con = DriverManager.getConnection(dbUrl,username,password);
					PreparedStatement st = con.prepareStatement(query + i + ",'" + nama + "'," + harga + ",'" + merek + "')");
				){		st.execute();
						System.out.println("\ndata berhasil ditambahkan");
					}
				catch (SQLException e ) {
					e.printStackTrace();
				}
			break;
	
		case "2" :
			MenuPenjual();
			break;
			
		default :
			System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
			}
		lanjut = pilih.equals("1") || pilih.equals("2");
		} }
	
	
	boolean lanjut2 = false;
	
	while(!lanjut2) {
	System.out.println("\n1.\tTambah Data Lagi");
	System.out.println("2.\tKembali ke Menu Penjual");
	System.out.print("Pilihan Anda : ");
	String pilih2 = scanTambah.nextLine();
	
	switch (pilih2) {
	case "1" :
		tambahBarang();
		break;
		
	case "2" :
		penjual();
		break;
		
	default :
		System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
		}
	lanjut = pilih2.equals("1") || pilih2.equals("2");
	} scanTambah.close();

}

protected static void editBarang() {
	Scanner scanEdit = new Scanner (System.in);
	System.out.println("\n=======================EDIT BARANG========================");
	
	boolean lanjut = false;
	while(!lanjut){
	System.out.println("1.\tLanjutkan");
	System.out.println("2.\tkembali");
	System.out.print("Silahkan pilih : ");
	String pilih = scanEdit.next();
	
	switch (pilih) {
	case "1" :
		menuEdit();
		break;

	case "2" :
		MenuPenjual();
		break;
		
	default :
		System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
		}
	lanjut = pilih.equals("1") || pilih.equals("2");
	}
	scanEdit.close();
}

protected static void menuEdit() {
	Scanner scanMenuEdit = new Scanner(System.in);
	daftarBarang();
	
	String username = "Analog";
	String password = "12345";
	String dbUrl = "jdbc:mysql://localhost:3306/finalp";
	boolean menuEdit = false;
	
	System.out.print("\n Masukan id barang yang akan diubah : ");
	String kode = scanMenuEdit.nextLine();
	
	try (
			Connection con = DriverManager.getConnection(dbUrl,username,password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select id_barang from barang Where id_barang = " + kode);
		){		if (rs.next()) {
				menuEdit = true;
				} else {
					System.out.println("id tidak ditemukan !!!");
					con.close();
					MenuPenjual();
				} con.close();
				
			}
		catch (SQLException e ) {
			System.out.println("something wrong!!");
		}
	
	if (menuEdit) {
		
	boolean lanjutEdit = false;
	while (!lanjutEdit) {
	System.out.println("\n1.\tNama Barang");
	System.out.println("2.\tMerek");
	System.out.println("3.\tHarga");
	System.out.println("4.\tKembali");
	System.out.print("\nSilahkan pilih yang akan diubah : ");
	String pilihEdit = scanMenuEdit.nextLine();
	
	switch (pilihEdit) {
	case "1" :
		System.out.print("masukan nama barang yang baru : ");
		String namaBaru = scanMenuEdit.nextLine();
		try (
				Connection con = DriverManager.getConnection(dbUrl,username,password);
				PreparedStatement st = con.prepareStatement("Update barang set namabarng = '" + namaBaru + "' Where id_barang = " + kode);
			){		st.execute();
					System.out.println("\ndata berhasil diubah");
					daftarBarang();
					MenuPenjual();
				}
			catch (SQLException e ) {
				System.out.println("something wrong!!");
			} break;
			
	case "2" :
		System.out.print("masukan nama merek baru : ");
		String merekBaru = scanMenuEdit.nextLine();
		try (
				Connection con = DriverManager.getConnection(dbUrl,username,password);
				PreparedStatement st = con.prepareStatement("Update barang set merek = '" + merekBaru + "' Where id_barang = " + kode);
			){		st.execute();
					System.out.println("\ndata berhasil diubah");
					daftarBarang();
					MenuPenjual();
				}
			catch (SQLException e ) {
				System.out.println("something wrong!!");
			} break;
			
	case "3" :
		System.out.print("masukan harga yang baru : ");
		String hargaBaru = scanMenuEdit.nextLine();
		try (
				Connection con = DriverManager.getConnection(dbUrl,username,password);
				PreparedStatement st = con.prepareStatement("Update barang set harga = " + hargaBaru + " Where id_barang = " + kode);
			){		st.execute();
					System.out.println("\ndata berhasil diubah");
					daftarBarang();
					MenuPenjual();
				}
			catch (SQLException e ) {
				System.out.println("something wrong!!");
			} break;
			
	case "4" :
		editBarang();
		break;
	default :
		System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
		}
	
	lanjutEdit = pilihEdit.equals("1") || pilihEdit.equals("2")|| pilihEdit.equals("3")|| pilihEdit.equals("4")|| pilihEdit.equals("5");
	
	}
	}scanMenuEdit.close(); 
		}

protected static void hapusBarang() {
	daftarBarang();
	
	System.out.println("\n=======================HAPUS BARANG=======================");
	Scanner scanHapus = new Scanner(System.in);
	
	String username = "Analog";
	String password = "12345";
	String dbUrl = "jdbc:mysql://localhost:3306/finalp";
	boolean hapus = false;
	String query ="delete from barang where id_Barang = ";
	System.out.print("id Barang : ");
	String id = scanHapus.next();
	
	
	
	try (
			Connection con = DriverManager.getConnection(dbUrl,username,password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select id_barang from barang Where id_barang = " +  id);
		){		if (rs.next()) {
				hapus = true;
				} else {
					System.out.println("id tidak ditemukan !!!");
					con.close();
					MenuPenjual();
				}
			}
		catch (SQLException e ) {
			System.out.println("something wrong!!");
		}
	
	if (hapus) {
	try (
			Connection con = DriverManager.getConnection(dbUrl,username,password);
			PreparedStatement st = con.prepareStatement(query + id +"");
		){		st.execute();
				System.out.println("\ndata berhasil dihapus");
				daftarBarang();
				System.out.println("=================================================================");
			} 
		catch (SQLException e ) {
			System.out.println("something wrong!!");
		}
	
	boolean lanjut = false;
	
	while (!lanjut) {
	System.out.println("\n1.\tHapus Lagi");
	System.out.println("2.\tkembali");
	System.out.print("Silahkan pilih : ");
	String pilih = scanHapus.next();
	
	switch (pilih) {
	case "1" :
		hapusBarang();
		break;

	case "2" :
		MenuPenjual();
		break;
		
	default :
		System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
		}
	lanjut = pilih.equals("1") || pilih.equals("2");
	} 
} scanHapus.close();
}
protected static void pesanBarang() {
	Scanner scanPesanBarang = new Scanner(System.in);
	List<String> ar = new ArrayList<>(); 

	System.out.println("\n=======================PESAN BARANG=======================");
	String username = "Analog";
	String password = "12345";
	String dbUrl = "jdbc:mysql://localhost:3306/finalp";
	String query = "insert into pesanan value (";
	int i = 1;
	boolean cekid = false;
			
			
	System.out.print("\n\nMasukan ID barang yang akan dibeli : ");
	String ID = scanPesanBarang.nextLine();
	ar.add(ID);
	System.out.print("Masukan Nama lengkap anda : ");
	String nama = scanPesanBarang.nextLine();
	ar.add(nama);
	System.out.print("Masukan Nohp : ");
	String nohp = scanPesanBarang.nextLine();
	ar.add(nohp);
	System.out.print("Masukan Alamat Penerima : ");
	String alamat = scanPesanBarang.nextLine();
	ar.add(alamat);
	System.out.print("Masukan jumlah barang yang akan dibeli (angka) : ");
	int jlpesanan = scanPesanBarang.nextInt();
	
	System.out.println("\nData Pesanan anda adalah : "+ar);
	
	try (
			Connection con = DriverManager.getConnection(dbUrl,username,password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select id_pembeli from pesanan ");
	){		while(rs.next()) {
			i++;
			} 
		cekid = true;
	}catch (SQLException e ) {
		System.out.println("something wrong!!");
		pembeli();
	}
	
	
	
	if (cekid) {
	try (
			Connection con = DriverManager.getConnection(dbUrl,username,password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from barang where id_barang = " + ID);
	){		
		if (rs.next()) {
			System.out.println("Barang yang dipesan : " + rs.getString("NamaBarng")+" merek "+ rs.getString("merek"));
			System.out.println("Total harga yang harus dibayar : "+rs.getInt("harga")*jlpesanan);
	} else {
		System.out.println(" \nid_Barang tidak ditemukan !! \n silahkan cek kembali id barang yang anda masukan");
		pembeli();
	}
	}catch (SQLException e ) {
		System.out.println("something wrong!!");
		pembeli();
	}
	
	boolean lanjut = false;
	while(!lanjut){
		System.out.println("\n1.\tKonfirmasi Pesanan");
		System.out.println("2.\tBatalkan");
		System.out.print("Silahkan pilih : ");
		String pilih = scanPesanBarang.next();
		
		switch (pilih) {
		case "1" :
			try (
					Connection con = DriverManager.getConnection(dbUrl,username,password);
					PreparedStatement st = con.prepareStatement(query + i + ",'" + nama + "'," + ID + "," + nohp + ",'"+ alamat + "'," + jlpesanan + ")");
				){		st.execute();
						System.out.println("\nPesanan Telah Dikonfirmasi");
					}
				catch (SQLException e ) {
					System.out.println("something wrong!!");
				}
			break;
	
		case "2" :
			pembeli();
			break;
			
		default :
			System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
			}
		lanjut = pilih.equalsIgnoreCase("1") || pilih.equalsIgnoreCase("2");
		}
	
	boolean lanjut2 = false;
	
	while (!lanjut2) {
	System.out.println("\n1.\tpesan Lagi");
	System.out.println("2.\tkembali");
	System.out.print("Silahkan pilih : ");
	String pilih2 = scanPesanBarang.next();
	
	switch (pilih2) {
	case "1" :
		pesanBarang();
		break;

	case "2" :
		pembeli();
		break;
		
	default :
		System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
		}
	lanjut2 = pilih2.equals("1") || pilih2.equals("2");
	}scanPesanBarang.close();
}
}

protected static void MenuPenjual () {
	Scanner scanMenuPenjual = new Scanner(System.in);
	boolean lanjut = false;
	while(!lanjut){
			System.out.println("\n1.\t daftar pesanan");
			System.out.println("2.\t Hapus data Barang");
			System.out.println("3.\t edit data Barang");
			System.out.println("4.\t tambah data Barang");
			System.out.println("5.\t ganti password");
			System.out.println("6.\t menu utama");
			System.out.print("\nSilahkan Pilih : ");
			String pilih = scanMenuPenjual.next();
			
			switch (pilih) {
			case "1" :
				daftarpesanan();
				break;
		
			case "2" :
				hapusBarang();
				break;
				
			case "3" :
				editBarang();
				break;
				
			case "4" :
				tambahBarang();
				break;
				
			case "5" :
				gantiPassword();
				break;
				
			case "6" :
				menuawal();
			default :
				System.out.println("\nPilihan tidak tersedia, silahkan pilihan yang ada!\n");
				}
			lanjut = pilih.equals("1") || pilih.equals("2")|| pilih.equals("3")|| pilih.equals("4")|| pilih.equals("5") || pilih.equals("6");
			}scanMenuPenjual.close();
}

protected static void gantiPassword() {
	Scanner scanGantiPass = new Scanner(System.in);
	System.out.print("\nMasukan username : ");
	String user = scanGantiPass.nextLine();
	System.out.print("Masukan password : ");
	String pass = scanGantiPass.nextLine();
	
	String username = "Analog";
	String password = "12345";
	String dbUrl = "jdbc:mysql://localhost:3306/finalp";
	String query = "select username,password from login where username = '" + user + "' and password ='" + pass + "'";
		
		try (
				Connection con = DriverManager.getConnection(dbUrl,username,password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
		){
			if (rs.next()) {
				
				System.out.print("Masukan password baru : ");
				String passBaru =  scanGantiPass.nextLine();
					
				try (
						PreparedStatement st2 = con.prepareStatement("update login set password ='" + passBaru + "' where username ='" + user + "'");
				){		
					st2.execute();
					System.out.println("password berhasil diubah !!");
					MenuPenjual();
						}
						
				catch (SQLException e ) {
					System.out.println("something wrong!!");
				}
				
				} else {
					System.out.println("username atau password salah !!");
					MenuPenjual();
				}
				
		}catch (SQLException e ) {
			System.out.println("something wrong!!");
		} scanGantiPass.close();
} 
}
