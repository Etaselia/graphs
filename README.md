# Matrix Class

The `Matrix` class represents a matrix and provides various operations and functionalities for matrix manipulation.

## Constructors

- `Matrix(int sideLength, int sideWidth, boolean random)`: Constructs a matrix with the specified side length and side width. If `random` is `true`, the matrix is filled with random values. Otherwise, the matrix is initialized with zeros. The name of the matrix is set to "Custom".

- `Matrix(int[][] matrix, String name)`: Constructs a matrix using the given 2D array `matrix` and assigns it the specified `name`. The side length and side width are determined based on the dimensions of the input matrix.

## Methods

- `getName()`: Returns the name of the matrix.

- `getSideLength()`: Returns the side length of the matrix.

- `getSideWidth()`: Returns the side width of the matrix.

- `getMatrix()`: Returns the 2D array representing the matrix.

- `setMatrix(int[][] matrix)`: Sets the matrix to the given 2D array.

- `setName(String name)`: Sets the name of the matrix.

- `loadCsv(String file) throws MatrixException`: Loads a matrix from a CSV file specified by the `file` parameter. The CSV file should be in a format where each row represents a matrix row, and elements within each row are separated by semicolons. The loaded matrix is returned as a `Matrix` object.

- `saveCsv(String file) throws MatrixException`: Saves the matrix to a CSV file specified by the `file` parameter. The matrix is written to the file in a format where each row represents a matrix row, and elements within each row are separated by semicolons.

- `toString()`: Returns a string representation of the matrix. The matrix is formatted as a tab-separated grid of values, with each row on a new line. The string includes the name of the matrix.

- `print()`: Prints the string representation of the matrix to the console.








# Matrix Multiplication Algorithm

This algorithm multiplies two matrices `matrixA` and `matrixB` and returns the resulting matrix. It follows the standard matrix multiplication algorithm.

## Signature

```java
public static Matrix multiply(Matrix matrixA, Matrix matrixB) throws MatrixException
```

### Parameters

- `matrixA`: The first matrix to be multiplied.
- `matrixB`: The second matrix to be multiplied.

### Returns

- `Matrix`: The resulting matrix after multiplying `matrixA` and `matrixB`.

### Exceptions

- `MatrixException`: If the dimensions of the matrices are not compatible for multiplication (i.e., the number of columns in `matrixA` is not equal to the number of rows in `matrixB`).

## Algorithm

1. Check if the number of columns in `matrixA` is equal to the number of rows in `matrixB`. If not, throw a `MatrixException` indicating that the matrices cannot be multiplied.
2. Create a new result matrix with dimensions `matrixA.getSideLength()` by `matrixB.getSideWidth()`.
3. Initialize each element of the result matrix to zero.
4. Perform nested loops over the rows of `matrixA`, the columns of `matrixB`, and the common dimension of the matrices.
   - For each element in the result matrix at position `(i, j)`, iterate over the common dimension `k` and calculate the dot product of the corresponding row in `matrixA` and the corresponding column in `matrixB`.
   - Accumulate the product in the result matrix at position `(i, j)`.
5. Return a new `Matrix` object initialized with the result matrix and a name indicating that it has been multiplied.

---

# Matrix Power Algorithm

This algorithm raises a matrix to a specified power `times` and returns the resulting matrix. It uses the matrix multiplication algorithm described above.

## Signature

```java
public Matrix power(int times) throws MatrixException
```

### Parameters

- `times`: The power to which the matrix should be raised.

### Returns

- `Matrix`: The resulting matrix after raising the current matrix to the specified power.

### Exceptions

- `MatrixException`: If the dimensions of the matrix are not compatible for multiplication.

## Algorithm

1. Create two `Matrix` objects: `initialMatrix` and `workingMatrix`, both initialized with the current matrix (`this`).
2. Perform a loop from `1` to `times - 1` (exclusive).
   - Within each iteration, update the `workingMatrix` by multiplying it with the `initialMatrix` using the matrix multiplication algorithm described above.
3. Set the name of the `workingMatrix` to indicate the power it has been raised to.
4. Return the `workingMatrix` as the result.

---

# Matrix Distance Algorithm

This algorithm calculates the distance matrix for a given matrix. The distance matrix represents the shortest path between any two elements in the matrix. It uses matrix powers and matrix multiplication to iteratively compute the distance matrix.

## Signature

```java
public Matrix distance() throws MatrixException
```

### Returns

- `Matrix`: The distance matrix for the current matrix.

### Exceptions

- `MatrixException`: If the dimensions of the matrix are not compatible for multiplication.

## Algorithm

1. Create a `Matrix` object `currentMatrix` initialized with the current matrix (`this`).
2. Create a new 2D array `distanceMatrix` with dimensions `this.getSideLength()` by `this.getSideWidth()`.
3. Initialize each element of the `distanceMatrix` to zero.
4. Initialize `matrixLength` to the side length of the matrix.
5. Initialize `powercount` to `1`.
6. Start

 an infinite loop.
   - Perform nested loops over the rows and columns of the matrix.
   - For each element in the `distanceMatrix` at position `(i, j)`, check if it is zero and the corresponding element in `currentMatrix` is greater than zero.
     - If so, set the value of the element in `distanceMatrix` at position `(i, j)` to `powercount`.
   - Check if `powercount` is equal to `matrixLength`.
     - If so, break the loop.
   - Increment `powercount` by `1`.
   - Update `currentMatrix` by raising it to the power of `powercount` using the `power` method described above.
1. Set the diagonal elements of the `distanceMatrix` to zero.
2. Return a new `Matrix` object initialized with the `distanceMatrix` and a name indicating that it represents the distance matrix
# Matrix Eccentricity Algorithm

This algorithm calculates the eccentricity matrix for a given matrix. The eccentricity of an element represents the maximum distance between that element and any other element in the matrix. It uses the `distance` method and array manipulation operations.

## Signature

```java
public Matrix eccentricity() throws MatrixException
```

### Returns

- `Matrix`: The eccentricity matrix for the current matrix.

### Exceptions

- `MatrixException`: If the dimensions of the matrix are not compatible for matrix operations.

## Algorithm

1. Invoke the `distance` method on the current matrix to obtain the distance matrix.
2. Create a new 2D array `eccentricityArray` with dimensions `1` by `this.sideWidth`.
3. Perform a loop over the rows of the `distance` matrix.
   - For each row, find the maximum value using `Collections.max` on the array obtained by converting the row from a primitive `int` array to an `Integer` list using `ArrayUtils.toObject`.
   - Set the value of the corresponding element in `eccentricityArray` to the maximum value.
4. Return a new `Matrix` object initialized with the `eccentricityArray` and a name indicating that it represents the eccentricity matrix.

---

# Matrix Radius Algorithm

This algorithm calculates the radius of a given matrix. The radius represents the minimum eccentricity value among all elements in the matrix. It uses the `eccentricity` method and array manipulation operations.

## Signature

```java
public Matrix radius() throws MatrixException
```

### Returns

- `Matrix`: The radius of the current matrix.

### Exceptions

- `MatrixException`: If the dimensions of the matrix are not compatible for matrix operations.

## Algorithm

1. Create a new 2D array `radius` with dimensions `1` by `1`.
2. Obtain the eccentricity matrix by invoking the `eccentricity` method on the current matrix.
3. Find the minimum value among all elements in the eccentricity matrix using `Collections.min` on the array obtained by converting the row of eccentricity values to an `Integer` list using `ArrayUtils.toObject`.
4. Set the value of the element in `radius` at position `(0, 0)` to the minimum value.
5. Return a new `Matrix` object initialized with the `radius` array and a name indicating that it represents the radius.

---

# Matrix Diameter Algorithm

This algorithm calculates the diameter of a given matrix. The diameter represents the maximum eccentricity value among all elements in the matrix. It uses the `eccentricity` method and array manipulation operations.

## Signature

```java
public Matrix diameter() throws MatrixException
```

### Returns

- `Matrix`: The diameter of the current matrix.

### Exceptions

- `MatrixException`: If the dimensions of the matrix are not compatible for matrix operations.

## Algorithm

1. Create a new 2D array `diameter` with dimensions `1` by `1`.
2. Obtain the eccentricity matrix by invoking the `eccentricity` method on the current matrix.
3. Find the maximum value among all elements in the eccentricity matrix using `Collections.max` on the array obtained by converting the row of eccentricity values to an `Integer` list using `ArrayUtils.toObject`.
4. Set the value of the element in `diameter` at position `(0, 0)` to the maximum value.
5. Return a new `Matrix` object initialized with the `diameter` array and a name indicating that it represents the diameter.

# Matrix Center Algorithm

This algorithm calculates the center of a given matrix. The center consists of the elements with the same eccentricity value as the radius. It uses the `radius` and `eccentricity` methods, as well as array manipulation operations.

## Signature

```java
public Matrix center() throws MatrixException
```

### Returns

- `Matrix`: The center of the current matrix.

### Exceptions

- `MatrixException`: If the dimensions of the matrix are not compatible for matrix operations.

## Algorithm

1. Obtain the radius value by accessing the element at position `(0, 0)` in the `radius` matrix obtained by invoking the `radius` method on the current matrix.
2. Obtain the eccentricity matrix by invoking the `eccentricity` method on the current matrix.
3. Initialize `returnLength` to `0`.
4. Perform a loop over the elements in the row of eccentricity values.
   - For each element at index `i`, check if it is equal to the radius value.
     - If so, increment `returnLength` by `1`.
5. Create a new 2D array `returnArray` with dimensions `1` by `returnLength`.
6. Reset `returnLength` to `0`.
7. Perform another loop over the elements in the row of eccentricity values.
   - For each element at index `i`, check if it is equal to the radius value.
     - If so, set the value of the element in `returnArray` at position `(0, returnLength)` to `i`.
     - Increment `returnLength` by `1`.
8. Return a new `Matrix` object initialized with the `returnArray` and a name indicating that it represents the center.

---

# Matrix Components Algorithm

This algorithm calculates the connected components in a given matrix. A connected component represents a group of elements that are directly or indirectly connected to each other. It uses a Depth-First Search (DFS) algorithm to traverse the matrix and identify the components.

## Signature

```java
public Matrix components()
```

### Returns

- `Matrix`: The components of the current matrix.

## Algorithm

1. Create a boolean array `visited` of size `sideLength` to keep track of visited elements.
2. Create an empty list `components` to store the identified components.
3. Perform a loop over the elements in the range `0` to `sideLength - 1`.
   - For each element at index `i`, check if it has not been visited (`visited[i]` is `false`).
     - If so, create a new empty list `component` to represent the current component.
     - Invoke the `componentDFS` method passing the matrix, `visited`, `i`, and `component`.
     - Add the `component` to the `components` list.
4. Create a new 2D array `returnMatrix` with dimensions `components.size()` by variable length.
5. Perform a loop over the elements in the `components` list.
   - For each component at index `i`, retrieve the component list.
   - Create a new 1D array `componentArray` of size `component.size()`.
   - Perform another loop over the elements in the `component` list.
     - For each element at index `j`, set the value of the element in `componentArray` at position `j` to `component.get(j)`.
   - Set the value of the element in `returnMatrix` at position `(i, j)` to `componentArray`.
6. Return a new `Matrix` object initialized with the `returnMatrix` and a name indicating that it represents the components.

---

# Component Depth-First Search (DFS) Algorithm

This algorithm is a helper method used by the `components` method to perform a Depth-First Search (DFS) traversal of the matrix and identify the elements in a connected component.

## Signature

```java
private void componentDFS(int[][] passedMatrix, boolean[] visited, int vertex, List<Integer> component)
```

### Parameters

- `passedMatrix`: The matrix to perform the DFS traversal on.
- `visited`: A boolean array to track visited elements.
- `vertex`: The current vertex being explored.
- `component`: The list to store the elements in the connected component.

## Algorithm

1. Mark the current vertex `visited[vertex]` as `true`.
2. Add the current vertex `vertex` to the `component` list.
3. Perform a loop over the elements in the row `passedMatrix[vertex]`.
   - For each element at index `i`, check if it is equal to `1` and has not been visited (`!visited[i]` is `true`).
     - If so, recursively invoke the `componentDFS` method passing `passedMatrix`, `visited`, `i`, and `component`.


# Matrix Articulation Points Algorithm

This algorithm calculates the articulation points in a given matrix. Articulation points, also known as cut vertices, are the vertices in a graph whose removal would disconnect the graph or create additional connected components. The algorithm utilizes a Depth-First Search (DFS) approach to identify the articulation points.

## Signature

```java
public Matrix articulationPoints()
```

### Returns

- `Matrix`: A matrix containing the identified articulation points.

## Algorithm

1. Create an empty list `articulationPoints` to store the identified articulation points.
2. Create integer arrays `discoveryTime`, `lowTime`, `parent`, and a boolean array `visited`, all of size `sideLength`, to track relevant information during the DFS traversal.
3. Fill the `parent` array with `-1` to indicate no parent initially.
4. Initialize `time` to `0`.
5. Perform a loop over the elements in the range `0` to `sideLength - 1`.
   - For each element at index `i`, check if it has not been visited (`visited[i]` is `false`).
     - If so, invoke the `articulationPointsDFS` method passing the matrix, `i`, `visited`, `discoveryTime`, `lowTime`, `parent`, `articulationPoints`, and `time`.
6. Create a new 2D array `returnMatrix` with dimensions `1` by the size of `articulationPoints`.
7. Perform a loop over the elements in the `articulationPoints` list.
   - For each element at index `i`, set the value of the element in `returnMatrix` at position `(0, i)` to `articulationPoints.get(i)`.
8. Return a new `Matrix` object initialized with the `returnMatrix` and a name indicating that it represents the articulation points.

---

# Articulation Points Depth-First Search (DFS) Algorithm

This algorithm is a helper method used by the `articulationPoints` method to perform a Depth-First Search (DFS) traversal of the matrix and identify the articulation points.

## Signature

```java
private void articulationPointsDFS(int[][] passedMatrix, int vertex, boolean[] visited, int[] discoveryTime, int[] lowTime, int[] parent, List<Integer> articulationPoints, int time)
```

### Parameters

- `passedMatrix`: The matrix to perform the DFS traversal on.
- `vertex`: The current vertex being explored.
- `visited`: A boolean array to track visited vertices.
- `discoveryTime`: An array to store the discovery time of each vertex.
- `lowTime`: An array to store the lowest reachable time for each vertex.
- `parent`: An array to store the parent vertex of each vertex.
- `articulationPoints`: A list to store the identified articulation points.
- `time`: The current time during the DFS traversal.

## Algorithm

1. Mark the current vertex `visited[vertex]` as `true`.
2. Initialize `childCount` to `0`.
3. Set the `discoveryTime` of the current vertex `vertex` to `time`.
4. Set the `lowTime` of the current vertex `vertex` to `time`.
5. Perform a loop over the elements in the row `passedMatrix[vertex]`.
   - For each element at index `i`, check if it is equal to `1`.
     - If so, check if the vertex `i` has not been visited (`!visited[i]` is `true`).
       - If true, increment `childCount` by `1`.
       - Set the `parent` of vertex `i` to `

vertex`.
       - Invoke the `articulationPointsDFS` method passing `passedMatrix`, `i`, `visited`, `discoveryTime`, `lowTime`, `parent`, `articulationPoints`, and `time + 1`.
       - Update the `lowTime` of vertex `vertex` by taking the minimum of its current value and the `lowTime` of vertex `i`.
       - Check if the current vertex `vertex` is the root of the DFS tree (i.e., `parent[vertex] == -1`) and if `childCount` is greater than `1`.
         - If true, add `vertex` to the `articulationPoints` list.
       - Otherwise, if the current vertex `vertex` is not the root and the `lowTime` of vertex `i` is greater than or equal to the `discoveryTime` of vertex `vertex`.
         - If true, add `vertex` to the `articulationPoints` list.
     - Else if vertex `i` is not the parent of the current vertex `vertex`.
       - Update the `lowTime` of vertex `vertex` by taking the minimum of its current value and the `discoveryTime` of vertex `i`.

# Matrix Euler Cycle Algorithm

This algorithm calculates the Euler cycle in a given matrix. An Euler cycle is a path in a graph that visits every edge exactly once and returns to the starting vertex. The algorithm utilizes a recursive approach to find the Euler cycle.

## Signature

```java
public Matrix eulerCycle()
```

### Returns

- `Matrix`: A matrix containing the Euler cycle.

## Algorithm

1. Check if the matrix does not have an Euler cycle by invoking the `hasEulerCycle` method.
   - If false, return a new `Matrix` object initialized with an empty 2D array and a name indicating that it represents the Euler cycle.
2. Create a copy of the matrix named `adjMatrix` by iterating over the rows and using `Arrays.copyOf` to copy each row's elements.
3. Create an empty list `cycle` to store the vertices of the Euler cycle.
4. Invoke the `findCycle` method with arguments `0`, `adjMatrix`, and `cycle` to find the Euler cycle.
5. Create a new 2D array `returnArray` with dimensions `1` by the size of `cycle`.
6. Iterate over the elements in `cycle`.
   - For each element at index `count`, set the value of the element in `returnArray` at position `(0, count)` to the current element in `cycle`.
7. Return a new `Matrix` object initialized with the `returnArray` and a name indicating that it represents the Euler cycle.

---

# Euler Cycle Recursive Algorithm

This algorithm is a helper method used by the `eulerCycle` method to find the Euler cycle in a matrix recursively.

## Signature

```java
void findCycle(int vertex, int[][] adjMatrix, List<Integer> cycle)
```

### Parameters

- `vertex`: The current vertex being explored.
- `adjMatrix`: The adjacency matrix representing the graph.
- `cycle`: A list to store the vertices of the Euler cycle.

## Algorithm

1. Iterate over the vertices in the range `0` to `adjMatrix.length - 1`.
   - For each vertex at index `nextVertexIterator`, check if there is an edge between the current vertex `vertex` and the next vertex `nextVertexIterator` (`adjMatrix[vertex][nextVertexIterator] > 0`).
     - If true, decrement the edge count between `vertex` and `nextVertexIterator` in `adjMatrix` by setting them to `0`.
     - Recursively invoke the `findCycle` method with arguments `nextVertexIterator`, `adjMatrix`, and `cycle`.
2. Add the current vertex `vertex` to the `cycle` list.


# Matrix Euler Line Algorithm

This algorithm calculates the Euler line in a given matrix, which is similar to the Euler cycle but starts at a specific vertex. The algorithm reuses existing code from the Euler cycle calculation to find the Euler line.

## Signature

```java
public Matrix eulerLine()
```

### Returns

- `Matrix`: A matrix containing the Euler line.

## Algorithm

1. Create a new `adjMatrix` as a copy of the matrix by iterating over the rows and using `Arrays.copyOf` to copy each row's elements.
2. Create an empty list `cycle` to store the vertices of the Euler line.
3. Try the following:
   - Invoke the `hasEulerPath` method to obtain a vertex with an uneven grade.
   - Invoke the `findCycle` method with the obtained vertex, `adjMatrix`, and `cycle` to find the Euler line.
4. If an exception of type `MatrixException` is caught, return a new `Matrix` object initialized with an empty 2D array and a name indicating that it represents the Euler line.
5. Create a new 2D array `returnArray` with dimensions `1` by the size of `cycle`.
6. Iterate over the elements in `cycle`.
   - For each element at index `count`, set the value of the element in `returnArray` at position `(0, count)` to the current element in `cycle`.
7. Return a new `Matrix` object initialized with the `returnArray` and a name indicating that it represents the Euler line.

---

# Check for Euler Path

This algorithm checks whether an Euler path exists in the given matrix, which can contain two vertices with uneven grades. An Euler path is a path in a graph that visits every edge exactly once.

## Signature

```java
private int hasEulerPath() throws MatrixException
```

### Throws

- `MatrixException`: If no Euler path exists.

### Returns

- `int`: The vertex with an uneven grade.

## Algorithm

1. Initialize `unevenGrades` and `unevenVertex` to `0`.
2. Check if the number of components in the matrix is `1` (connected graph).
   - If true, iterate over the rows in the matrix.
     - For each row at index `i`, calculate the sum of its elements by iterating over the columns.
       - If the sum is not divisible by `2`, set `unevenVertex` to `i` and increment `unevenGrades` by `1`.
   - If `unevenGrades` is equal to `2`, return `unevenVertex`.
3. Throw a `MatrixException` with the message "noEulerPath" to indicate that no Euler path exists.

---

# Check for Euler Cycle

This algorithm checks whether an Euler cycle exists in the given matrix. An Euler cycle is a cycle in a graph that visits every edge exactly once.

## Signature

```java
private Boolean hasEulerCycle()
```

### Returns

- `Boolean`: `true` if an Euler cycle exists, `false` otherwise.

## Algorithm

1. Check if the number of components in the matrix is `1` (connected graph).
   - If true, iterate over the rows in the matrix.
     - For each row `row`, calculate the sum of its elements by iterating over the values.
       - If the sum is not divisible by `2`, return `false` as the graph contains uneven grade degrees.
   - Return `true` as all grade degrees in the graph are even.
2. Return `false` as the graph is not connected.

