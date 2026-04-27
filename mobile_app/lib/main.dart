import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Gestion Étudiants',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.indigo,
        useMaterial3: true,
      ),
      home: const EtudiantListPage(),
    );
  }
}

class Departement {
  final int id;
  final String nom;

  Departement({required this.id, required this.nom});

  factory Departement.fromJson(Map<String, dynamic> json) {
    return Departement(
      id: json['id'],
      nom: json['nom'],
    );
  }
}

class Etudiant {
  final int id;
  final String cin;
  final String nom;
  final String dateNaissance;

  Etudiant({
    required this.id,
    required this.cin,
    required this.nom,
    required this.dateNaissance,
  });

  factory Etudiant.fromJson(Map<String, dynamic> json) {
    return Etudiant(
      id: json['id'],
      cin: json['cin'],
      nom: json['nom'],
      dateNaissance: json['dateNaissance'],
    );
  }
}

class EtudiantListPage extends StatefulWidget {
  const EtudiantListPage({super.key});

  @override
  State<EtudiantListPage> createState() => _EtudiantListPageState();
}

class _EtudiantListPageState extends State<EtudiantListPage> {
  List<Etudiant> etudiants = [];
  List<Departement> departements = [];
  Departement? selectedDepartement;
  String? errorMessage;
  bool isLoading = true;
  // Note: Use '10.0.2.2' instead of 'localhost' if testing on Android Emulator
  // Note: Use your machine's IP address if testing on a real device
  final String baseUrl = 'http://localhost:8090/api';

  @override
  void initState() {
    super.initState();
    fetchInitialData();
  }

  Future<void> fetchInitialData() async {
    setState(() => errorMessage = null);
    await fetchDepartements();
    await fetchEtudiants();
  }

  Future<void> fetchDepartements() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/departements'));
      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        setState(() {
          departements = data.map((e) => Departement.fromJson(e)).toList();
        });
      } else {
        setState(() => errorMessage = 'Erreur départements: ${response.statusCode}');
      }
    } catch (e) {
      setState(() => errorMessage = 'Erreur connexion API: $e');
      print('Error fetching departments: $e');
    }
  }

  Future<void> fetchEtudiants() async {
    setState(() => isLoading = true);
    try {
      String url = '$baseUrl/etudiants';
      if (selectedDepartement != null) {
        url += '?departementId=${selectedDepartement!.id}';
      }
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        setState(() {
          etudiants = data.map((e) => Etudiant.fromJson(e)).toList();
          isLoading = false;
        });
      } else {
        setState(() {
          isLoading = false;
          errorMessage = 'Erreur étudiants: ${response.statusCode}';
        });
      }
    } catch (e) {
      print('Error fetching students: $e');
      setState(() {
        isLoading = false;
        errorMessage = 'Erreur connexion API: $e';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Gestion des Étudiants'),
        backgroundColor: Colors.indigo,
        foregroundColor: Colors.white,
      ),
      body: Column(
        children: [
          if (errorMessage != null)
            Container(
              color: Colors.red.shade100,
              width: double.infinity,
              padding: const EdgeInsets.all(8),
              child: Text(errorMessage!, style: const TextStyle(color: Colors.red)),
            ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Row(
              children: [
                const Text('Département: ', style: TextStyle(fontWeight: FontWeight.bold)),
                const SizedBox(width: 10),
                Expanded(
                  child: DropdownButton<Departement>(
                    isExpanded: true,
                    hint: const Text('Tous les départements'),
                    value: selectedDepartement,
                    items: [
                      const DropdownMenuItem<Departement>(
                        value: null,
                        child: Text('Tous'),
                      ),
                      ...departements.map((d) => DropdownMenuItem(
                        value: d,
                        child: Text(d.nom),
                      )),
                    ],
                    onChanged: (value) {
                      setState(() {
                        selectedDepartement = value;
                      });
                      fetchEtudiants();
                    },
                  ),
                ),
              ],
            ),
          ),
          Expanded(
            child: isLoading
                ? const Center(child: CircularProgressIndicator())
                : RefreshIndicator(
                    onRefresh: fetchEtudiants,
                    child: etudiants.isEmpty
                        ? const Center(child: Text('Aucun étudiant trouvé'))
                        : ListView.builder(
                            itemCount: etudiants.length,
                            itemBuilder: (context, index) {
                              final e = etudiants[index];
                              return Card(
                                margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                                elevation: 2,
                                child: ListTile(
                                  leading: const CircleAvatar(
                                    child: Icon(Icons.person),
                                  ),
                                  title: Text(e.nom, style: const TextStyle(fontWeight: FontWeight.bold)),
                                  subtitle: Text(
                                    'CIN: ${e.cin}\nNé le: ${e.dateNaissance}',
                                  ),
                                  isThreeLine: true,
                                ),
                              );
                            },
                          ),
                  ),
          ),
        ],
      ),
    );
  }
}