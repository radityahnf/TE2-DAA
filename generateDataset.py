from random import randint
def generate_datasets():
    small_dataset = [randint(0, 100) for _ in range(10)]
    medium_dataset = [randint(0, 100) for _ in range(40)]
    big_dataset = [randint(0, 100) for _ in range(80)]

    return small_dataset, medium_dataset, big_dataset

def export_dataset_to_file(dataset, filename):
    with open(filename, 'w') as file:
        for item in dataset:
            file.write(str(item) + '\n')

small_dataset, medium_dataset, big_dataset = generate_datasets()

export_dataset_to_file(small_dataset, "small_dataset.txt")
export_dataset_to_file(medium_dataset, "medium_dataset.txt")
export_dataset_to_file(big_dataset, "big_dataset.txt")