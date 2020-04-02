
JFLAGS = -g
JCLASS = -cp ./src:.:$(CLASSPATH):/usr/share/java/junit4-4.5.jar: # on mills
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $(JCLASS) $*.java

CLASSES = \
			src/me3/a4/Controller.java \
	    src/me3/a4/BoardT.java \
			src/me3/a4/GameState.java \
			src/me3/a4/StateMoves.java \
	    src/me3/a4/StateScore.java \
		  src/me3/a4/StateTime.java \
			src/me3/a4/view/View.java \

MAIN = AllTests

default: classes

classes: $(CLASSES:.java=.class)

doc:
	doxygen doxConfig
	cd latex && $(MAKE)

test: src/$(MAIN).class
	$(JVM) $(JCLASS) org.junit.runner.JUnitCore $(MAIN)

clean:
	rm -rf html
	rm -rf latex
	cd src
	find . -iname "*.class" -exec rm {} \;
	find . -type f -name '*.aux' -delete
	find . -type f -name '*.fdb_latexmk' -delete
	find . -type f -name '*.synctex.gz' -delete
	find . -type f -name '*.fls' -delete
	find . -type f -name '*.out' -delete
