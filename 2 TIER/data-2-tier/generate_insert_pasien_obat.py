import random
from faker import Faker

fake = Faker('id_ID')

NUM_PASIEN = 1000
NUM_OBAT = 200
FILE_NAME = 'insert_pasien_obat.sql'

obat_names = [
    'Paracetamol', 'Amoxicillin', 'Ibuprofen', 'Asam Mefenamat',
    'CTM', 'Antasida', 'Vitamin C', 'Vitamin D',
    'Omeprazole', 'Loratadine', 'Metformin', 'Amlodipine'
]

kategori_obat = [
    'Analgesik', 'Antibiotik', 'Vitamin',
    'Antihistamin', 'Antasida', 'Antihipertensi'
]

dosis_list = ['1x1', '2x1', '3x1', '1x2']

with open(FILE_NAME, 'w', encoding='utf-8') as f:

    # Pakai database yang SUDAH ADA
    f.write("USE naura_farma;\n\n")

    # ================= INSERT PASIEN =================
    f.write("INSERT INTO pasien (nama, email, umur, jenis_kelamin) VALUES\n")

    for i in range(1, NUM_PASIEN + 1):
        nama = f"{fake.first_name()} {fake.last_name()}".replace("'", "''")
        email = fake.email()
        umur = random.randint(18, 75)
        jk = random.choice(['Laki-laki', 'Perempuan'])

        line = f"('{nama}', '{email}', {umur}, '{jk}')"
        line += ",\n" if i < NUM_PASIEN else ";\n"
        f.write(line)

    # ================= INSERT OBAT =================
    f.write("\nINSERT INTO obat (nama_obat, kategori, dosis, harga, stok) VALUES\n")

    for i in range(1, NUM_OBAT + 1):
        nama_obat = random.choice(obat_names)
        kategori = random.choice(kategori_obat)
        dosis = random.choice(dosis_list)
        harga = random.randint(2000, 50000)
        stok = random.randint(10, 300)

        line = f"('{nama_obat}', '{kategori}', '{dosis}', {harga}, {stok})"
        line += ",\n" if i < NUM_OBAT else ";\n"
        f.write(line)

print("Insert data acak BERHASIL dibuat!")
print("File:", FILE_NAME)
