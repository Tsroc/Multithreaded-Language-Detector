Project: A Multithreaded Language Detector
Author: Eoin Wilkie

Runner class:
Contains classes:
		Files
		Producer
		Consumer
		Database

	Class Files:
		Stores location of the dataset file and query file.
		getContent(): Returns query file as a String for parsing.

	Class Producer implements Runnable:
		Producer(): Takes a String args which is taken from dataset.
		Run(): Reads dataset contents and adds it to the BlockingQueue(size: 10), 1 line at a time. 
		
	Class Consumer implements Runnable:
		Consumer(): Takes the blocking queue and nGram # as args.
		Run(): Executes an ExecutorService(threadpool size: 10) which parses the line with Class Parse and adds it to the Database.

Producer continues until no lines remain and then inserts "exit" into the Queue and then exits..
Consumer reads until "exit" is read and then exits.
When both finish, the database which has been constructed is resized and the queryfile is analyzed to get the closest matching language.

	Database Class:
		Stores information in a ConcurrentHashMap<Language, Map<Integer, LanguageEntry>>.
		add(): Identifies the language and converts CharSequence to its HashCode, then checks if db contains this entry. Increments the count if so, else adds it to the db.
		getLanguageEntries(): Reads Language name, if there is not an entry for this language it will be created. Language entry for this language is returned. Used for adding to the language map.
		Resize(): Gets top # entries. Uses getTop(#)
		getTop(): Adds a weight to the language entries. Most frequent = 1, orders Map.

		getLanguage(): returns calculation of language - used with queryfile.
		getOutOfPlaceDistance(): Calculates distance of queryfile from languages in db, lowest value = closest match.

	Parser Class:
		Used within the Consumer Class and builds the database within the Database Class.
		
		run(): splits the line at '@' character, [0] = String to be parsed, [1] = language.
			passes these 2 Strings into parse().
		parse(): loops over the String to be parsed, adds to DB in nGram sized CharSequence's.
		
		analyseQuery: Analyses the queryfile. Parses the file in similarly to the above parse() method and 
			adds it to a HashMap. The Map is then sorted and weight is added.
			finally, db.getLanguage() is called on the newly constructed map.		