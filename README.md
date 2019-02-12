# library-multithread
Trivial library database management system based on binary search trees sorted, double circular list, singly link listed ordered. Storing the data in a multi-thread server accessible by a client that implements the user interface.

Design a trivial library database management system based on binary search trees with the following menu:
1. Add a genre
2. Add a book
3. Modify a book.
4. List all genre
5. List all book by genre
6. List all book for a particular genre
7. Search for a book
8. Exit

Project’s architecture
The list of genres was implemented using a binary search tree sorted based on genre’s title. Each node in this tree contain two attributes: Genre’s title and a sorted double circular list with books’ information. For each book, the authors list was implemented using a singly linked list ordered by author’s last name.
The project defines a multi-thread server where the data must be stored and a client that implements the user interface.
