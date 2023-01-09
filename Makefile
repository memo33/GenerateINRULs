dist: jar
	zip GenerateINRULs-1.0.1.zip \
		GenerateINRULs.jar \
		GenerateINRULs.bat \
		README.md \
		example/*
jar: build
	jar cmf src/MANIFEST.MF GenerateINRULs.jar -C target/ rulUtil
build:
	javac -d target/ src/main/java/rulUtil/GenerateINRULs.java
