import csv

import numpy as np
import pandas as pd
from scipy.spatial.distance import cdist
from sklearn.decomposition import PCA
import matplotlib.pyplot as plt
import itertools


def KMeans(points, no_of_clusters, iterations):
    # Choose the centroids of the clusters
    centroids = points[np.random.choice(points.shape[0], no_of_clusters, replace=False), :]
    clusters = None
    # Repeat the process for each iteration
    for iteration in range(iterations):
        # Assign each points the closest centroid
        distances = cdist(points, centroids, 'euclidean')
        clusters = np.argmin(distances, axis=1)
        # Find the new centroids
        centroids = np.array([np.mean(points[clusters == index], axis=0) for index in range(no_of_clusters)])
    return clusters


def readPoints():
    points = []
    results = []
    with open('dataset.csv') as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        for row in csv_reader:
            if row[0] != 'label':
                row_tuple = (float(row[1]), float(row[2]))
                points.append(row_tuple)
                results.append(row[0])
    return np.array(points), results


def plotData(cluster_data, group_field):
    # Plot each group with a different color
    groups = cluster_data.groupby(group_field)
    for name, group in groups:
        plt.plot(group["val1"], group["val2"], marker="o", linestyle="", label=name)
    plt.legend()
    plt.show()


def alignLabels(true_labels, found_labels):
    # Try to align the labels with maximum similarity
    best_permutation = None
    best_similarity = 0
    for permutation in itertools.permutations(['A', 'B', 'C', 'D']):
        permuted_labels = [permutation[label] for label in found_labels]
        similarity = sum(label1 == label2 for label1, label2 in zip(true_labels, permuted_labels))
        if similarity > best_similarity:
            best_permutation = permuted_labels
            best_similarity = similarity
    return best_permutation


def statistics(cluster_data, label_column, found_column):
    # We need to compute the confusion matrix, on which we will calculate the statistics
    conf_matrix = np.zeros((4, 4))
    label_map = {'A': 0, 'B': 1, 'C': 2, 'D': 3}
    for label_col, found_col in zip(cluster_data[label_column], cluster_data[found_column]):
        conf_matrix[label_map[label_col]][label_map[found_col]] += 1
    # Find accuracy
    print(f'Total accuracy is: {np.trace(conf_matrix) / np.sum(conf_matrix)}')
    scores_matrix = np.zeros((4, 4))
    for label_index in range(4):
        # Find the statistics for each label
        tp = conf_matrix[label_index][label_index]
        fn = np.sum(conf_matrix[label_index]) - tp
        fp = np.sum(conf_matrix[:, label_index]) - tp
        tn = np.sum(conf_matrix) - tp - fn - fp
        accuracy = (tp + tn) / (tp + tn + fp + fn)
        precision = tp / (tp + fp)
        recall = tp / (tp + fn)
        f1_score = 2 * tp / (2 * tp + fp + fn)
        scores_matrix[label_index] = np.array([accuracy, precision, recall, f1_score])
    print('The statistics are: ')
    print(pd.DataFrame(scores_matrix, columns=['accuracy', 'precision', 'recall', 'f1_score'], index=['A', 'B', 'C', 'D']))


if __name__ == "__main__":
    # Load the data
    data, data_labels = readPoints()

    # # Add this for PCA (it doesn't change much)
    # pca = PCA(2)
    # data = pca.fit_transform(np.array(data))

    # Find the clusters using KMeans
    cluster_labels = KMeans(data, 4, 5000)
    # Align the labels to perform the analysis
    aligned_labels = alignLabels(data_labels, cluster_labels)

    # Plot the clusters
    pd_data = pd.DataFrame(data, columns=['val1', 'val2'])
    pd_data['trueLabels'] = data_labels
    pd_data['clusterLabels'] = aligned_labels
    plotData(pd_data, 'clusterLabels')

    # Compute statistics
    statistics(pd_data, 'trueLabels', 'clusterLabels')