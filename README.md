# resume

A resume renderer application that will go over the inputted YAML files, parsing, converting and rendering them on PDF and Markdown outputs. The resulting files may be found on the [output](./output/) folder.

The Markdown output content will be focused only on experiences and will be composed of the "description" and "tasks" fields. The purpose of this is to have an easy access alternative to copy and paste on third party professional platforms.

As for the PDF output, it's rendered based on the result HTML from the current Thymeleaf [template HTML](./src/main/resources/templates/resume.html) with no identifying metadata to increase anonymity.

## generating resumes

The rendering retrieves and parses the data provided on the input YAML file, so for it to handle correctly the inputted fields it should follow the expected format. The format in question may be found on the [example file](./input/example.yml) provided, or you may use the template below to write your own from scratch. Please note that required fields are annotated with an `asterisk*` at the end.

```yaml
language: [en-us | pt-br]*
name: string*
role: string*
linkedin: url*
github: url*
email: email*
phone: phone*

experiences: # array (0+)
  - company: # required
      name: string*
      website: url
    client:
      name: string*
      website: url
    role: string*
    from: year-month*
    to: year-month*
    description: string*
    tasks: # array (0+)
      - string*
    skills: # array (0+)
      - string*

degrees: # array (0+)
  - institution: string*
    degree: string*
    from: year*
    to: year*

certifications: # array (0+)
  - institution: string*
    certification: string*
    year: year*
```

## docker

To run the application in Docker, you simply build the image.

```sh
docker build . -t resume
```

Then run the container at the default spring boot port.

```sh
docker run -v ./input:/usr/app/input -v ./output:/usr/app/output --rm resume
```
