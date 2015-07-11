INSERT INTO AlertSubscriber
	   (FirstName, LastName, DateAdded, Email) 
	   VALUES
	   ('Russell', 'Smith', GETDATE(), 'RussellSmith@gmail.com')
	 , ('Ashley', 'Miller', GETDATE(), 'AshleyMiller@gmail.com')
	 , ('Bonnie', 'Rogers', GETDATE(), 'BonnieRogers@gmail.com')
	 , ('Daniel', 'King', GETDATE(), 'DanielKing@gmail.com')
	 , ('John', 'Garcia', GETDATE(), 'JohnGarcia@gmail.com')
	 , ('Doris', 'Diaz', GETDATE(), 'DorisDiaz@gmail.com')
	 , ('Nicholas', 'Richardson', GETDATE(), 'NicholasRichardson@gmail.com')
	 , ('Angela', 'Perry', GETDATE(), 'AngelaPerry@gmail.com')
	 , ('Kathleen', 'Thompson', GETDATE(), 'KathleenThompson@gmail.com')
	 , ('Nancy', 'Edwards', GETDATE(), 'NancyEdwards@gmail.com')

INSERT INTO VOC
	   (SensorName, DangerZone, ProtectionInformation, SymptomInformation)
	   VALUES
	   ('Tellurium Platinochloride', 521, 'The public solves the eastern photo. An acoustic unseen dies over the galaxy. Should the dance dance? Can her mumble dance with the giving accent? The deaf toasts the blame behind an average drug.', 'The highway instructs the applicant. Why does the rotating rubbish ribbon the hindsight? Any daughter emerges on top of the remembered spectacular. A medicine rolls every cobbler.')
	 , ('Carbon Aurochloride', 869, 'A corrupting trilogy resides in a noted machine. The attack malfunctions against an associate. How will a designer burden the typical music? The tried seal worries.', 'Any vacuum advertises! The boss reasons around the history. A cube chalks before the sail. With a designer fasts the gate. The vicar shakes the graduated lecture under a captain.')
	 , ('Calcium Cuprochloride', 677, 'When will the bell rule? Why does the lisp multiply? When can your propaganda kernel hurt? Why does the dependence interact with an insufficient whistle? The snobbery worries the aircraft. A postal exhaust scratches the nerve beneath the influential courage.', 'A historical calculator revolts. A lord bobs. Her paradise exists near an ace kiss. Your known follower peers past the solo manufacturer.')
	 , ('Beryllium Cuprochloride', 713, 'Can the seventh calculus pardon the jammed goldfish? When can an ahead farm persist? A citizen evolves the awaited western. A downhill rests on top of the milk. The traveled attendant attributes a parameter opposite the informed priest. When will the medicine alarm the model toy?', 'A marginal suspicion bicycles in his vocal pointer. The compelling pole plays under the organized cinema. An aunt colors? How does the cigarette smile?')
	 , ('Boron Tellurate', 140, 'A cheating psychologist wrongs the holder. Under the provisional undesirable suffers a prejudiced yeti. A heresy smells! When will the distinctive creep accept into this altogether?', 'A platform bobs opposite another concert. The designer plates a theorem throughout the talented highway. The hysterical mass gates the adapted bastard. Any pedal experiments throughout a frown. The plate traces a freezing enterprise outside the coach.')
	 , ('Platinum Platinochloride', 103, 'The terminator companions a catholic flour. Will the chairman succeed? The attached choral boosts your creature. Does a corrected search monkey the expensive width? A box overcomes with the accompanying cylinder.', 'The driver plants the unobtainable changeover. The competitive screen behaves outside a bye lesson. How can the movie feel the growing chemical? The bigger elitist multiplies. The disguise ruins a human into the latest mass.')
	 , ('Manganese Carbonate', 456, 'The enforced coast detracts the ladder around a void giant. The mumble fails. A patience reigns. How can the jargon toe the line?', 'The fixed wild speaks. The grave leans against the preliminary gulf. The seeking ratio witnesses the terse fossil. The changeover intervenes under the campus.')
	 , ('Seaborgium Selenate', 929, 'The face detracts the literary cash above the unseen. The sea marches! A paranoid lies below every doe. The comparison cultures an ignorant hypothesis across a capital. Why cant a god smile in the ill representative?', 'The refrain twists below a crisp! The telephone burns opposite the device! This foreigner fights the business law. The furthest identifier flips opposite the road. The funded wizard shelves the appearance.')
	 , ('Fluorine Fluoride', 724, 'A luck participates opposite a hard symbol. The vacuum detects a garbled book. Why cant a market succeed? His pin rests. How can the student defect across its recreational fear?', 'Past the practised intelligence reflects the performing cleaner. The myth barks. An identifying paint sticks a tribe into the nice west. The boss addresses a trace around the sound iron.')
	 , ('Malic Acid', 583, 'A sacked fuss bundles a ruling reform. An approval bends the doubtless vacuum near a timetable. When will the flagging workshop advance? A firm bigotry prevails into whatever beloved.', 'A correspondence patronizes the grey beer. A pain decks the concealed tube. The tear charms an excited shelter. The detector repeats a reversed waste. The optimal context sighs.')
	 
GO

INSERT INTO Monitor
	   (VOCID, Latitude, Longitude, Zip, SerialNumber)
	   VALUES
	   (0, 42.255261, -121.789879, '97601', 64)
	 , (1, 42.255357, -121.786774, '97601', 65)
	 , (2, 42.256308, -121.787001, '97601', 66)
	 , (3, 42.257383, -121.788828, '97601', 67)
	 , (4, 42.254457, -121.784094, '97601', 68)
	 , (5, 42.257789, -121.785867, '97603', 69)
	 , (6, 42.256149, -121.785352, '97603', 70)
	 , (7, 42.253348, -121.787428, '97603', 71)
	 , (8, 42.254449, -121.784631, '97603', 72)
	 , (9, 42.254880, -121.782552, '97603', 73)


INSERT INTO AlertSubscriber_MonitorList
	   (AlertSubscriberID, MonitorID)
	   VALUES
	   (0, 0)
	 , (1, 1)
	 , (2, 2)
	 , (3, 3)
	 , (4, 4)
	 , (5, 5)
	 , (6, 6)
	 , (7, 7)
	 , (8, 8)
	 , (9, 9)

GO

INSERT INTO VOCReading
	   (MonitorID, Level, Date, Accuracy)
	   VALUES
	   (0, 556, GETDATE(), 1)
	 , (1, 474, GETDATE(), 1)
	 , (2, 269, GETDATE(), 1)
	 , (3, 647, GETDATE(), 1)
	 , (4, 489, GETDATE(), 1)
	 , (5, 643, GETDATE(), 1)
	 , (6, 383, GETDATE(), 1)
	 , (7, 945, GETDATE(), 1)
	 , (8, 523, GETDATE(), 1)
	 , (9, 819, GETDATE(), 1)
 GO