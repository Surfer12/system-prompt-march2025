from setuptools import setup, find_packages

setup(
    name="uplift-system",
    version="0.1.0",
    packages=find_packages(),
    install_requires=[
        "PyYAML>=6.0.1",
        "jpype1>=1.4.1",
        "typing-extensions>=4.7.1",
    ],
    author="Ryan Oates",
    author_email="ryanoatie@outlook.com",
    description="A metaphorical exploration of technological interoperability",
    keywords="uplift, interoperability, mojo, java, swift, cpp",
    python_requires=">=3.9",
) 